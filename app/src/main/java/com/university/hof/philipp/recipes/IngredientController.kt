package com.university.hof.philipp.recipes

import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList

/**
 * Created by philipp on 20.12.17.
 */
class IngredientController {

    private var ingredientList = IngredientList()

    init {
        createIngredients()
    }

    fun getList() : IngredientList {
        return ingredientList
    }

    private fun createIngredients() {
        ingredientList.addIngredient(Ingredient())
    }
}