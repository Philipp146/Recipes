package com.university.hof.philipp.recipes.Model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.util.*

/**
 * Created by patrickniepel on 29.11.17.
 */
//The model that gets updated when the download of the general recipes has finished
class RecipeListModel : ViewModel(), Observer {

    private var recipeListData: MutableLiveData<RecipeList> = MutableLiveData()


    init {
        recipeListData.value = RecipeListSingleton.instance.recipeListData
        RecipeListSingleton.instance.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        Log.d("OBSERVER", RecipeListSingleton.instance.recipeListData.recipes.size.toString())
        recipeListData.value = RecipeListSingleton.instance.recipeListData
    }


    fun getRecipeListData(): MutableLiveData<RecipeList> {
        return recipeListData
    }


    fun getElement(at: Int): RecipeLeftOvers {
        return recipeListData.value!!.recipes[at]
    }

    fun clear() {
        recipeListData = MutableLiveData()
    }
}