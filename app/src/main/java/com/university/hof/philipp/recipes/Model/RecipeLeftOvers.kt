package com.university.hof.philipp.recipes.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by patrickniepel on 29.11.17.
 */
//A recipe used for the leftovers and the general recipes overview screen
data class RecipeLeftOvers(@SerializedName("recipe_id") val id : String,
                           @SerializedName("title") val title : String,
                           @SerializedName("image_url") val imgUrl : String,
                           @SerializedName("social_rank") val sRank : Float
)