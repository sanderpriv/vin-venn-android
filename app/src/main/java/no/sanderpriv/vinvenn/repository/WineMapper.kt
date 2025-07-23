package no.sanderpriv.vinvenn.repository

import no.sanderpriv.vinvenn.api.WineDto
import no.sanderpriv.vinvenn.domain.Wine

object WineMapper {
    fun map(wines: List<WineDto>): List<Wine> = wines.map {

        Wine(
            id = it.id,
            header = Wine.HeaderInfo(
                name = it.name,
                price = it.price,
                url = it.url,
                category = it.category,
                volume = it.volume,
                origin = it.origin,
            ),
            bodyInfo = Wine.BodyInfo(
                score = it.score,
                imageUrl = it.imageUrl,
                description = it.description,
                fullness = it.fullness,
                freshness = it.freshness,
                tannin = it.tannin,
                aroma = it.aroma,
                taste = it.taste,
                alcoholPercentage = it.alcoholPercentage,
                acid = it.acid,
                sugar = it.sugar,
                grape = it.grape,
            ), recommendationOrder = it.recommendationOrder
        )
    }
}

