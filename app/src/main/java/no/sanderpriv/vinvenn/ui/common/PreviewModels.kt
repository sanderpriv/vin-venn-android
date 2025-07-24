package no.sanderpriv.vinvenn.ui.common

import no.sanderpriv.vinvenn.domain.Wine

object PreviewModels {
    val wine = Wine(
        id = 0,
        header = Wine.HeaderInfo(
            "Alamos Malbec 2023",
            159.9,
            "https://www.vinmonopolet.no",
            "Rødvin",
            "75 cl",
            "Argentina, Mendoza"
        ),
        bodyInfo = Wine.BodyInfo(
            score = 6.0,
            imageUrl = "",
            description = "",
            fullness = 4.0,
            freshness = 4.0,
            tannin = 4.0,
            aroma = "4.0",
            taste = "4.0",
            alcoholPercentage = 4.0,
            acid = 4.0,
            sugar = "4.0",
            grape = "4.0",
        ),
        recommendationOrder = 0,
    )

    val wines = listOf(
        wine,
        wine,
        wine,
        wine,
        wine.copy(recommendationOrder = 1),
        wine.copy(recommendationOrder = 2),
        wine.copy(recommendationOrder = 2),
    )
}
