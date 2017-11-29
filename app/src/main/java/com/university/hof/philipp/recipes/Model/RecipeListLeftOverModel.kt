package com.university.hof.philipp.recipes.Model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.util.*

/**
 * Created by patrickniepel on 29.11.17.
 */
//The model that gets updated when the download of the leftover recipes has finished
class RecipeListLeftOverModel : ViewModel(), Observer {

    private var recipeListLeftOverData: MutableLiveData<RecipeList> = MutableLiveData()


    init {
        recipeListLeftOverData.value = RecipeListSingleton.instance.recipeListLeftOverData
        RecipeListSingleton.instance.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        Log.d("OBSERVER", RecipeListSingleton.instance.recipeListLeftOverData.recipes.size.toString())
        recipeListLeftOverData.value = RecipeListSingleton.instance.recipeListLeftOverData
    }


    fun getRecipeListData(): MutableLiveData<RecipeList> {
        return recipeListLeftOverData
    }


    fun getElement(at: Int): RecipeLeftOvers {
        return recipeListLeftOverData.value!!.recipes[at]
    }

    fun clear() {
        recipeListLeftOverData = MutableLiveData()
    }
}