package no.sanderpriv.vinvenn.api

import com.google.gson.annotations.SerializedName

data class WineDto(
    @SerializedName("id") val id: Long,
    @SerializedName("navn") val name: String,
    @SerializedName("pris") val price: Double,
    @SerializedName("url") val url: String,
    @SerializedName("kategori") val category: String,
    @SerializedName("volum") val volume: String,
    @SerializedName("omraade") val origin: String,

    @SerializedName("score") val score: Double,
    @SerializedName("img") val imageUrl: String,
    @SerializedName("beskrivelse") val description: String,

    @SerializedName("fylde") val fullness: Double,
    @SerializedName("friskhet") val freshness: Double,
    @SerializedName("garvestoffer") val tannin: Double,

    @SerializedName("lukt") val aroma: String,
    @SerializedName("smak") val taste: String,
    @SerializedName("alkoholprosent") val alcoholPercentage: Double,
    @SerializedName("syre") val acid: Double,
    @SerializedName("sukker") val sugar: String,
    @SerializedName("drue") val grape: String,

    @SerializedName("anbefaling_nr") val recommendationOrder: Int,
)
