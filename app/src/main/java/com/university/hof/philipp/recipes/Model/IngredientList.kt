package com.university.hof.philipp.recipes.Model

/**
 * Created by philipp on 22.11.17.
 */
class IngredientList {

    private var ingredientList : ArrayList<Ingredient>
        get() = ingredientList
        set(value) {ingredientList = value}

    public fun addIngredient(i : Ingredient){
        ingredientList.add(i)
    }
}