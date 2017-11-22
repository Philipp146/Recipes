package com.university.hof.philipp.recipes.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by philipp on 22.11.17.
 */

data class Recipe(@SerializedName("recipe_id") val id : String,
                  @SerializedName("title") val title : String,
                  @SerializedName("image_url") val imgUrl : String,
                  @SerializedName("social_rank") val sRank : Float
)
