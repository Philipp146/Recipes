package com.university.hof.philipp.recipes.Download

/**
 * Created by philipp on 22.11.17.
 */
import com.university.hof.philipp.recipes.Model.IngredientList
import com.university.hof.philipp.recipes.Model.Recipe
import com.university.hof.philipp.recipes.Model.RecipeList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ClientInterfaceApi {

    @GET("search")
    fun getRecipes(@Query("key") apikey : String, @Query("q") ingredientList : String) : Call<RecipeList>

    @GET("get")
    fun getRecipe(@Query("key") apikey : String, @Query("rId") recipeId : String) : Call<Recipe>
}