package com.university.hof.philipp.recipes.Controller

import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.R

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

        val i = Ingredient()
        i.setName("Tomato")
        i.setImg(R.drawable.abc_ic_star_black_36dp)

        val i2 = Ingredient()
        i.setName("Cucumber")
        i.setImg(R.drawable.abc_ic_star_black_36dp)

        val i3 = Ingredient()
        i.setName("Onion")
        i.setImg(R.drawable.abc_ic_star_black_36dp)

        ingredientList.addIngredient(i)
        ingredientList.addIngredient(i2)
        ingredientList.addIngredient(i3)
    }
}