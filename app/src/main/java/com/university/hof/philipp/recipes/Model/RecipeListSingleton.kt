package com.university.hof.philipp.recipes.Model
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.Recipes.Recipe
import com.university.hof.philipp.recipes.Model.Recipes.RecipeContainer
import java.util.*

/**
 * Created by philipp on 22.11.17.
 */
class RecipeListSingleton private constructor() : Observable() {

        private object Holder {
            val INSTANCE = RecipeListSingleton()
        }

        var recipeListData = RecipeList(mutableListOf())
            set(value) {
                field = value
                setChanged()
                notifyObservers()
            }

        var recipeListLeftOverData = RecipeList(mutableListOf())
            set(value) {
                field = value
                setChanged()
                notifyObservers()
            }

        var recipeData = RecipeContainer(Recipe())
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
            recipeListLeftOverData = RecipeList(mutableListOf())
            recipeData = RecipeContainer(Recipe())
        }
}