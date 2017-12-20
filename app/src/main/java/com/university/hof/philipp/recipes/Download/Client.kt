package com.university.hof.philipp.recipes.Download

import android.util.Log
import com.google.gson.GsonBuilder
import com.university.hof.philipp.recipes.Model.*
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.Recipes.Recipe
import com.university.hof.philipp.recipes.Model.Recipes.RecipeContainer
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by patrickniepel on 22.11.17.
 */
class Client {

    private val URL : String = "http://food2fork.com/api/"

    //key -> "leftover", "recipe"
    public fun getRecipes(query : String, key : String) {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder().baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build()

        val api = retrofit.create(ClientInterfaceApi::class.java)

        val call = api.getRecipes("97dd5475c88b44ce08af3b18e46b8c3d", query)

        call.enqueue(object : Callback<RecipeList> {

            override fun onFailure(call: Call<RecipeList>?, t: Throwable?) {
                Log.d("FAILURE", t!!.message)
            }

            override fun onResponse(call: Call<RecipeList>?, response: Response<RecipeList>?) {
                val code = response!!.code()

                Log.d("TAAAAAAG", code.toString())
                val data = response.body()!!

                if (key == "leftover") {
                    RecipeListSingleton.instance.recipeListLeftOverData = data
                }
                if (key == "recipe") {
                    RecipeListSingleton.instance.recipeListData = data
                }

                Log.d("TAAAAAg", data.toString())
            }
        })
    }

    public fun getRecipe(recipeId : String){
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
        val api = retrofit.create(ClientInterfaceApi::class.java)

        val call = api.getRecipe("97dd5475c88b44ce08af3b18e46b8c3d", recipeId)
        call.enqueue(object : Callback<RecipeContainer> {
            override fun onFailure(call: Call<RecipeContainer>?, t: Throwable?) {
                Log.d("FAILURE", t!!.message)
            }

            override fun onResponse(call: Call<RecipeContainer>?, response: Response<RecipeContainer>?) {
                val code = response!!.code()
                Log.d("TAAAAAAG", code.toString())
                val data = response.body()!!
                RecipeListSingleton.instance.recipeData = data
            }
        })
    }
}