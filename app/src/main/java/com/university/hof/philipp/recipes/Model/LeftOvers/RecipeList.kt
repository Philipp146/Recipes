package com.university.hof.philipp.recipes.Model.LeftOvers

import com.google.gson.annotations.SerializedName

/**
 * Created by philipp on 22.11.17.
 */
//Model for the root json object for a list of recipes
data class RecipeList(@SerializedName("recipes") val recipes : MutableList<RecipeLeftOvers>
)