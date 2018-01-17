package com.university.hof.philipp.recipes.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import com.university.hof.philipp.recipes.Model.Recipes.Recipe
import com.university.hof.philipp.recipes.Model.Recipes.RecipeContainer
import com.university.hof.philipp.recipes.Model.Recipes.RecipeModel
import android.support.design.widget.TabLayout
import android.webkit.WebView
import android.widget.*
import com.university.hof.philipp.recipes.Adapter.DetailsListViewAdapter
import com.university.hof.philipp.recipes.MainActivity
import com.university.hof.philipp.recipes.R


/**
 * Created by patrickniepel on 06.12.17.
 */
class Details : Fragment() {

    private var adapter : DetailsListViewAdapter? = null

    private var listView : ListView? = null
    private var imageViewRecipe : ImageView? = null
    private var textViewPublisher : TextView? = null
    private var textViewTitle : TextView? = null

    private var detailsButton : Button? = null

    private var downloadFinished = false
    private var progressBar : ProgressBar? = null
    private var textLine : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DetailsListViewAdapter(context)
        setupLayout()
        setupObserver()

        //Hide TabLayout
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.GONE

        progressBar = activity.findViewById(R.id.progressBarDetails)
        progressBar!!.visibility = View.VISIBLE

        textLine = activity.findViewById(R.id.lineDetails)
        textLine!!.visibility = View.GONE

        //Download Details for Recipe
        if (!downloadFinished) {
            var id = arguments.getString("id")
            Client().getRecipe(id)
            downloadFinished = !downloadFinished
        }
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle("Recipe Details")
    }

    override fun onDetach() {
        super.onDetach()
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.setTitle("Recipes")
    }

    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeModel::class.java)

        model.getRecipeData().observe(this, Observer<RecipeContainer> { list ->
            Log.d("VIEWMODEL", list!!.recipe.toString())
            adapter!!.updateListData(list)
            updateLayout()
            //adapter!!.isFirstCall = false
        })
    }

    private fun updateLayout() {
        val recipeData = RecipeListSingleton.instance.recipeData

        if(!recipeData.recipe.imgUrl.isEmpty()) {
            Picasso.with(context).load(recipeData.recipe.imgUrl).fit().into(imageViewRecipe)
        }
        textViewPublisher!!.text = recipeData.recipe.publisher
        textViewTitle!!.text = recipeData.recipe.title
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.listViewIngredients)
        imageViewRecipe = activity.findViewById<ImageView>(R.id.imageViewRecipePicture)
        textViewPublisher = activity.findViewById<TextView>(R.id.textViewPublisherNameToFillIn)
        textViewTitle = activity.findViewById<TextView>(R.id.textViewTitleToFillIn)

        listView!!.adapter = adapter //Custom adapter telling listview what to render

        detailsButton = activity.findViewById<Button>(R.id.buttonLoadSourceUrl)
        setupDetailsListener()
    }

    private fun setupDetailsListener() {
        detailsButton!!.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                loadWebView()
            }
        })
    }

    private fun loadWebView() {
        val sourceUrl = RecipeListSingleton.instance.recipeData.recipe.sourceUrl
        val web = WebView(context)
        web.loadUrl(sourceUrl)
    }
}