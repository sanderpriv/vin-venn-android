package no.sanderpriv.vinvenn.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.sanderpriv.vinvenn.api.VinVennApi
import no.sanderpriv.vinvenn.db.MealsDb
import no.sanderpriv.vinvenn.db.VinVennCacheDao
import no.sanderpriv.vinvenn.domain.Meal
import no.sanderpriv.vinvenn.domain.Wine
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.seconds

class VinVennRepository(
    private val vinVennApi: VinVennApi,
    private val vinVennCacheDao: VinVennCacheDao,
) {

    private val warmUpScope = CoroutineScope(Dispatchers.IO)

    suspend fun getMeals(): Result<List<Meal>> {
        return repositoryCall(
            mapper = { dto -> MealsMapper.map(dto) }
        ) {
            val cache = vinVennCacheDao.getMealsDb()
            val cacheIsExpired = cache == null || cache.expirationDate.isExpired()
            if (!cacheIsExpired) {
                warmUpScope.launch {
                    delay(5.seconds)
                    vinVennApi.getMeals() // Ping api to warm it up despite cache not being expired
                }
                return@repositoryCall cache.dto
            }

            val response = vinVennApi.getMeals()
            if (!response.isSuccessful) {
                throw RuntimeException("Failed to fetch meals -> ${response.code()}: ${response.errorBody()}")
            }

            val dto = response.body()!!
            vinVennCacheDao.insertMeals(
                MealsDb(
                    dto = dto,
                    expirationDate = Date().apply { time += TimeUnit.DAYS.toMillis(1) } // Cache for 1 day
                )
            )

            dto
        }
    }

    suspend fun findWinesByMealId(mealId: String): Result<List<Wine>> = repositoryCall(
        mapper = { dto -> WineMapper.map(dto) },
    ) {
        vinVennApi.findWines(
            minPrice = 0,
            maxPrice = 2000,
            location = "bestillingsutvalget",
            searchString = mealId
        )
    }
}

suspend fun <T, Z> repositoryCall(
    context: CoroutineContext = Dispatchers.IO,
    mapper: suspend (T) -> Z,
    call: suspend () -> T,
): Result<Z> {
    return withContext(context) {
        try {
            Result.success(mapper(call()))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }
}
