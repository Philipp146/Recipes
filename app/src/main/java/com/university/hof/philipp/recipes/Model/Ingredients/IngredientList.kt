package com.university.hof.philipp.recipes.Model.Ingredients

import android.os.Parcel
import android.os.Parcelable
import com.university.hof.philipp.recipes.R
import java.io.Serializable

/**
 * Created by philipp on 22.11.17.
 */
class IngredientList() {

    private var ingredientList : ArrayList<Ingredient> = arrayListOf()

    fun addIngredient(i : Ingredient){
        ingredientList.add(i)
    }

    fun addIngredients(i : ArrayList<Ingredient>) {
        ingredientList.addAll(i)
    }

    fun getIngredient(pos : Int) : Ingredient{
        return ingredientList[pos]
    }

    fun getIngredientList() : ArrayList<Ingredient>{
        return ingredientList
    }

    fun setIngredientList(list : ArrayList<Ingredient>) {
        ingredientList = list
    }

    fun removeIngredient(pos : Int) {
        ingredientList.removeAt(pos)
    }

    fun addIngredientByString(str : String){
        val i : Ingredient = Ingredient()
        i.setName(str)
        i.setImg(R.drawable.ic_launcher_background)
        ingredientList.add(i)
    }
}