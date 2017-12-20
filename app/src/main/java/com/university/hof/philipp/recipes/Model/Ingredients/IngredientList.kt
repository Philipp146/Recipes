package com.university.hof.philipp.recipes.Model.Ingredients

import com.university.hof.philipp.recipes.R

/**
 * Created by philipp on 22.11.17.
 */
class IngredientList {

    private var ingredientList : ArrayList<Ingredient> = arrayListOf()

    fun addIngredient(i : Ingredient){
        ingredientList.add(i)
    }

    fun getIngredient(pos : Int) : Ingredient{
        return ingredientList[pos]
    }

    fun getIngredientList() : ArrayList<Ingredient>{
        return ingredientList
    }

    fun addIngredientByString(str : String){
        var i : Ingredient = Ingredient()
        i.setName(str)
        i.setImg(R.drawable.ic_launcher_background)
        ingredientList.add(i)
    }

}