package no.sanderpriv.vinvenn.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.sanderpriv.vinvenn.api.VinVennApi
import no.sanderpriv.vinvenn.domain.Meal
import no.sanderpriv.vinvenn.domain.Wine
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

class VinVennRepository(
    private val vinVennApi: VinVennApi
) {

    suspend fun getMeals(): Result<List<Meal>> {
        return repositoryCall(
            mapper = { dto -> MealsMapper.map(dto) }
        ) { vinVennApi.getMeals() }
    }

    suspend fun findWines(searchString: String): Result<List<Wine>> = repositoryCall(
        mapper = { dto -> WineMapper.map(dto) },
    ) {
        vinVennApi.findWines(
            minPrice = 0,
            maxPrice = 2000,
            location = "bestillingsutvalget",
            searchString = searchString
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
