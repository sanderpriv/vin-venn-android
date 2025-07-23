package no.sanderpriv.vinvenn.api

import retrofit2.http.GET
import retrofit2.http.Query


interface VinVennApi {

    @GET("matrett")
    suspend fun getMeals(): MealsDto

    @GET("finn_vin")
    suspend fun findWines(
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("location") location: String,
        @Query("searchString") searchString: String,
    ): List<WineDto>
}
