package com.university.hof.philipp.recipes.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by philipp on 22.11.17.
 */
//A single Recipe for the recipe details screen
data class Recipe(
        @SerializedName("publisher") val publisher : String,
        @SerializedName("publisher_url") val publisherUrl : String,
        @SerializedName("source_url") val sourceUrl : String,
        @SerializedName("ingredients") val ingredients : ArrayList<String>,
        @SerializedName("title") val title : String,
        @SerializedName("image_url") val imgUrl : String,
        @SerializedName("social_rank") val sRank : Float
)
