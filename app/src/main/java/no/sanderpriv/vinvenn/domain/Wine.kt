package no.sanderpriv.vinvenn.domain

data class Wine(
    val id: Long,
    val header: HeaderInfo,
    val bodyInfo: BodyInfo,
    val recommendationOrder: Int,
) {
    data class HeaderInfo(
        val name: String,
        val price: Double,
        val url: String,
        val category: String,
        val volume: String,
        val origin: String,
    )

    data class BodyInfo(
        val score: Double,
        val imageUrl: String,
        val description: String,

        val fullness: Double,
        val freshness: Double,
        val tannin: Double,

        val aroma: String,
        val taste: String,
        val alcoholPercentage: Double,
        val acid: Double,
        val sugar: String,
        val grape: String,
    )
}
