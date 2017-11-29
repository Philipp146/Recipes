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

    @Headers("Authorization: 97dd5475c88b44ce08af3b18e46b8c3d")
    @GET("search")
    fun getRecipes(@Query("key") apikey : String, @Query("q") ingredientList : String) : Call<RecipeList>
    @GET("get")
    fun getRecipe(@Query("key") apikey : String, @Query("rId") recipeId : String) : Call<Recipe>
}