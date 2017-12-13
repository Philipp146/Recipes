package com.university.hof.philipp.recipes.Model.Recipes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeLeftOvers
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import java.util.*

/**
 * Created by patrickniepel on 13.12.17.
 */
class RecipeModel : ViewModel(), Observer {

    private var recipeData: MutableLiveData<RecipeContainer> = MutableLiveData()


    init {
        recipeData.value = RecipeListSingleton.instance.recipeData
        RecipeListSingleton.instance.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        Log.d("OBSERVER", RecipeListSingleton.instance.recipeData.toString())
        recipeData.value = RecipeListSingleton.instance.recipeData
    }


    fun getRecipeData(): MutableLiveData<RecipeContainer> {
        return recipeData
    }


    fun getIngridient(at: Int): Recipe {
        return recipeData.value!!.recipe
    }

    fun clear() {
        recipeData = MutableLiveData()
    }
}