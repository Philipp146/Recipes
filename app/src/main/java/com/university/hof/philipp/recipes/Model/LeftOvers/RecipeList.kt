package com.university.hof.philipp.recipes.Model.LeftOvers

import com.google.gson.annotations.SerializedName

/**
 * Created by philipp on 22.11.17.
 */
data class RecipeList(@SerializedName("recipes") val recipes : MutableList<RecipeLeftOvers>
)