package com.university.hof.philipp.recipes.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.university.hof.philipp.recipes.Adapter.DetailsListViewAdapter
import com.university.hof.philipp.recipes.Controller.NetworkConnection
import com.university.hof.philipp.recipes.MainActivity
import com.university.hof.philipp.recipes.R


/**
 * Created by patrickniepel on 06.12.17.
 */

class Details : Fragment() {

    private val SHARED_PREFERENCES = "SHARED"
    private val COUNTER_ADS = "interstitialCounter"
    private var interstitialAdCounter = 0

    private var adapter : DetailsListViewAdapter? = null

    private var listView : ListView? = null
    private var imageViewRecipe : ImageView? = null
    private var textViewPublisher : TextView? = null
    private var textViewTitle : TextView? = null

    private var detailsButton : Button? = null

    private var downloadFinished = false
    private var progressBar : ProgressBar? = null
    private var textLine : TextView? = null

    private var interstitialAd: InterstitialAd? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        interstitialAdCounter = loadInterstitialAdCounter()
        interstitialAdCounter++
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hide TabLayout
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.GONE

        interstitialAd = newInterstitialAd()
        loadInterstitial()

        adapter = DetailsListViewAdapter(context, activity)
        setupLayout()

        setupObserver()

        textLine = activity.findViewById(R.id.lineDetails)
        textLine!!.visibility = View.GONE


        //Download Details for Recipe
        if (!downloadFinished) {
            progressBar = activity.findViewById(R.id.progressBarDetails)
            progressBar!!.visibility = View.VISIBLE

            var id = arguments.getString("id")
            Client().getRecipe(id)
            downloadFinished = !downloadFinished
        }
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle(getString(R.string.details))
        //showInterstitial()
    }

    override fun onDetach() {
        super.onDetach()
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.setTitle(getString(R.string.recipes))
        saveInterstitialAdCounter()
    }

    private fun newInterstitialAd() : InterstitialAd {
        val interstitialAd = InterstitialAd(context)
        interstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                showInterstitial()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
            }

            override fun onAdClosed() {
            }
        }
        return interstitialAd
    }

    private fun loadInterstitial() {
        val adRequest = AdRequest.Builder().setRequestAgent("android_studio:ad_template").build()
        interstitialAd!!.loadAd(adRequest)
    }

    private fun showInterstitial() {
        //Bei jedem zehnten Aufruf wird Werbung angezeigt
        if (interstitialAdCounter < 10) {
            return
        }
        else {
            interstitialAdCounter = 0
        }

        if (interstitialAd != null && interstitialAd!!.isLoaded()) {
            interstitialAd!!.show()
        }
    }

    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeModel::class.java)

        model.getRecipeData().observe(this, Observer<RecipeContainer> { list ->
            Log.d("VIEWMODEL", list!!.recipe.toString())
            adapter!!.updateListData(list)
            updateLayout()
            adapter!!.isFirstCall = false
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

        if (!NetworkConnection().isOnline(context)) {
            Toast.makeText(context, getString(R.string.connection), Toast.LENGTH_LONG).show()
            return
        }

        val sourceUrl = RecipeListSingleton.instance.recipeData.recipe.sourceUrl
        val web = WebView(context)
        web.loadUrl(sourceUrl)
    }

    private fun loadInterstitialAdCounter() : Int {
        val prefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val counter = prefs.getInt(COUNTER_ADS, 0)

        return counter
    }

    private fun saveInterstitialAdCounter() {

        val prefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()

        editor.putInt(COUNTER_ADS, interstitialAdCounter)
        editor.apply()
    }
}