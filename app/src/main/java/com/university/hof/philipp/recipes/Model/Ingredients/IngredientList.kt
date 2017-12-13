package com.university.hof.philipp.recipes.Model.Ingredients

/**
 * Created by philipp on 22.11.17.
 */
class IngredientList {

    private var ingredientList : ArrayList<Ingredient> = arrayListOf()

    public fun addIngredient(i : Ingredient){
        ingredientList.add(i)
    }
}