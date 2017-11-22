package com.university.hof.philipp.recipes.Model

import java.util.*

/**
 * Created by philipp on 22.11.17.
 */
class RecipeListSingleton private constructor() : Observable(){

    private object Holder {
            val INSTANCE = RecipeListSingleton()
        }

        var recipeListData = RecipeList(mutableListOf())
            set(value) {
                field = value
                setChanged()
                notifyObservers()
            }

        companion object {
            val instance: RecipeListSingleton by lazy { Holder.INSTANCE }
        }


        init {
            recipeListData = RecipeList(mutableListOf())
        }
}