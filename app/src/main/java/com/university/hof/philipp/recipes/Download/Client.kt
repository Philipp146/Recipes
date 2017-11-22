package com.university.hof.philipp.recipes.Download

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by patrickniepel on 22.11.17.
 */
class Client {

    private var retrofit: Retrofit? = null
    private val SEARCH_URL : String = "http://food2fork.com/api/search"
    private val RECIPE_URL : String = "http://food2fork.com/api/get"

/*
    fun getClient(key: String): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        retrofit?.let {

            if(key == "search") {
                retrofit = Retrofit.Builder()
                        .baseUrl(SEARCH_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
            }

            if(key == "recipe") {
                retrofit = Retrofit.Builder()
                        .baseUrl(RECIPE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
            }
        }

        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create()
                )

        val retrofit = builder
                .client(
                        httpClient.build()
                )
                .build()

        val client = retrofit.create(GitHubClient::class.java!!)

        return retrofit!!
    }*/
}