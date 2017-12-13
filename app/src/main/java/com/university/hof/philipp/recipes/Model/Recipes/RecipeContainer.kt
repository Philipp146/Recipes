package com.university.hof.philipp.recipes.Model.Recipes

import com.google.gson.annotations.SerializedName

/**
 * Created by patrickniepel on 13.12.17.
 */
data class RecipeContainer(@SerializedName("recipe") val recipe : Recipe
)