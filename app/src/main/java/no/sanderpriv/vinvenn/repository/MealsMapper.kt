package no.sanderpriv.vinvenn.repository

import no.sanderpriv.vinvenn.api.MealsDto
import no.sanderpriv.vinvenn.domain.Meal

object MealsMapper {
    fun map(meals: MealsDto): List<Meal> =
        meals.data.map { Meal(it.key, it.value) }
}

