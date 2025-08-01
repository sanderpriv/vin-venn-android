package no.sanderpriv.vinvenn.repository

import com.google.gson.internal.LinkedTreeMap
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import no.sanderpriv.vinvenn.api.MealsDto
import no.sanderpriv.vinvenn.api.VinVennApi
import no.sanderpriv.vinvenn.api.WineDto
import no.sanderpriv.vinvenn.db.MealsDb
import no.sanderpriv.vinvenn.db.VinVennCacheDao
import no.sanderpriv.vinvenn.domain.Meal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.Date
import java.util.concurrent.TimeUnit

class VinVennRepositoryTest {

    private lateinit var vinVennApi: VinVennApi
    private lateinit var cache: VinVennCacheDao
    private lateinit var repository: VinVennRepository

    @Before
    fun setUp() {
        vinVennApi = mockk<VinVennApi>()
        cache = mockk<VinVennCacheDao>(relaxed = true) {
            coEvery { getMealsDb() } returns null
        }
        repository = VinVennRepository(vinVennApi, cache)
    }

    @Test
    fun `getMeals returns success result`() = runTest {
        val mealsDto = MealsDto(
            data = LinkedTreeMap<String, String>().apply {
                put("Pizza", "id_1")
                put("Burger", "id_2")
            }
        )
        coEvery { vinVennApi.getMeals() } returns Response.success(mealsDto)
        val expectedMeals = listOf(
            Meal(id = "id_2", name = "Burger"),
            Meal(id = "id_1", name = "Pizza"),
        )
        val result = repository.getMeals()
        assertTrue(result.isSuccess)
        assertEquals(expectedMeals, result.getOrNull())
    }

    @Test
    fun `getMeals returns failure on exception`() = runTest {
        coEvery { vinVennApi.getMeals() } throws RuntimeException("API error")
        val result = repository.getMeals()
        assertTrue(result.isFailure)
    }

    // Test when cache is NOT expired (should use cache)
    @Test
    fun `getMeals uses cache when not expired`() = runTest {
        val cachedMealsDto = MealsDto(
            data = LinkedTreeMap<String, String>().apply {
                put("Sushi", "id_3")
            }
        )
        val cacheDb = MealsDb(
            dto = cachedMealsDto,
            expirationDate = Date().apply { time += TimeUnit.HOURS.toSeconds(2) }
        )
        coEvery { cache.getMealsDb() } returns cacheDb
        coEvery { vinVennApi.getMeals() } returns Response.success(cachedMealsDto)
        val result = repository.getMeals()
        assertTrue(result.isSuccess)
        assertEquals(listOf(Meal(id = "id_3", name = "Sushi")), result.getOrNull())
    }

    // Test when cache IS expired (should fetch from API)
    @Test
    fun `getMeals fetches from API when cache is expired`() = runTest {
        val expiredCacheDb = MealsDb(
            dto = MealsDto(data = LinkedTreeMap()),
            expirationDate = Date().apply { time -= TimeUnit.HOURS.toMillis(2) }
        )
        val apiMealsDto = MealsDto(
            data = LinkedTreeMap<String, String>().apply {
                put("Taco", "id_4")
            }
        )
        coEvery { cache.getMealsDb() } returns expiredCacheDb
        coEvery { vinVennApi.getMeals() } returns Response.success(apiMealsDto)
        val result = repository.getMeals()
        assertTrue(result.isSuccess)
        assertEquals(listOf(Meal(id = "id_4", name = "Taco")), result.getOrNull())
        coVerify(exactly = 1) { vinVennApi.getMeals() }
    }

    @Test
    fun `findWines returns success result`() = runTest {
        val wines = listOf(
            WineDto(
                id = 5848501,
                name = "Bourguignons 2022",
                price = 199.0,
                url = "https://www.vinmonopolet.no",
                category = "rødvin",
                volume = "75 cl",
                origin = "Frankrike, Burgund, Coteaux Bourguignons",
                score = 4.5,
                imageUrl = "https://bilder.vinmonopolet.no/cache/515x515-0/5848501-1.jpg",
                description = "desc",
                fullness = 3.0,
                freshness = 2.5,
                tannin = 1.5,
                aroma = "aroma",
                taste = "taste",
                alcoholPercentage = 13.0,
                acid = 5.6,
                sugar = "sugar",
                grape = "grape",
                recommendationOrder = 1
            )
        )
        coEvery {
            vinVennApi.findWines(
                minPrice = any(),
                maxPrice = any(),
                location = any(),
                searchString = "id_1",
            )
        } returns wines
        val result = repository.findWinesByMealId("id_1")
        assertTrue(result.isSuccess)
        val wine = result.getOrThrow().single()

        val header = wine.header
        assertEquals("Bourguignons 2022", header.name)
        assertEquals(199.0, header.price, 0.001)
        assertEquals("https://www.vinmonopolet.no", header.url)

        val body = wine.bodyInfo
        assertEquals(4.5, body.score, 0.001)
        assertEquals("https://bilder.vinmonopolet.no/cache/515x515-0/5848501-1.jpg", body.imageUrl)
        assertEquals(13.0, body.alcoholPercentage, 0.001)
    }
}
