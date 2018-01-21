package com.university.hof.philipp.recipes.Model.Recipes

import com.google.gson.annotations.SerializedName

/**
 * Created by patrickniepel on 13.12.17.
 */
//Model for the root json objects for a single recipe
data class RecipeContainer(@SerializedName("recipe") val recipe : Recipe
)