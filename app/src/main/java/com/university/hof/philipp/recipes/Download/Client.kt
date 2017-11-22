package com.university.hof.philipp.recipes.Download

import android.util.Log
import com.google.gson.GsonBuilder
import com.university.hof.philipp.recipes.Model.IngredientList
import com.university.hof.philipp.recipes.Model.RecipeList
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by patrickniepel on 22.11.17.
 */
class Client {

    private var retrofit: Retrofit? = null
    private val SEARCH_URL : String = "http://food2fork.com/api/"
    private val RECIPE_URL : String = "http://food2fork.com/api/get/"

    public fun getData(){
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder().baseUrl(SEARCH_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build()

        val api = retrofit.create(ClientInterfaceApi::class.java)

        val call = api.getRecipes("97dd5475c88b44ce08af3b18e46b8c3d", "chicken")
        call.enqueue(object : Callback<RecipeList> {

            override fun onFailure(call: Call<RecipeList>?, t: Throwable?) {
                Log.d("FAILURE", t!!.message)
            }

            override fun onResponse(call: Call<RecipeList>?, response: Response<RecipeList>?) {
                val code = response!!.code()
                Log.d("TAAAAAAG", code.toString())
                val data = response.body()!!
                RecipeListSingleton.instance.recipeListData = data
                Log.d("TAAAAAg", data.toString())
            }
        })
    }
}