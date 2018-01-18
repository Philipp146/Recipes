package com.university.hof.philipp.recipes.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.squareup.picasso.Picasso
import com.university.hof.philipp.recipes.Adapter.LeftoverRecipesListViewAdapter
import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeListLeftOverModel
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import com.university.hof.philipp.recipes.R
import com.university.hof.philipp.recipes.MainActivity


/**
 * Created by patrickniepel on 20.12.17.
 */
class LeftoverRecipes : Fragment() {

    private var adapter : LeftoverRecipesListViewAdapter? = null
    private var listView : ListView? = null
    private var searchData : String = ""
    private var emptyView : TextView? = null
    private var progressBar : ProgressBar? = null

    private var calledFragment = false

    private var adView : AdView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.leftover_recipes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hide TabLayout
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.GONE

        adapter = LeftoverRecipesListViewAdapter(context, activity)

        loadAdView()
        setupLayout()
        setupObserver()

        //Download ist bereits erfolgt wenn true
        if (!calledFragment) {
            startDownload()
        }

        adapter!!.updateListData()

        emptyView = activity.findViewById<TextView>(R.id.empty_view_leftovers)
        emptyView!!.visibility = View.GONE

    }

    override fun onResume() {
        super.onResume()
        var fab = activity.findViewById<FloatingActionButton>(R.id.fab)
        fab!!.hide()

        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle("Leftover Recipes")

        //Download ist bereits erfolgt wenn true
        if (!calledFragment) {
            progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
            progressBar!!.visibility = View.VISIBLE
        }

        calledFragment = true
    }

    override fun onDetach() {
        super.onDetach()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle("Recipes")
    }

    private fun loadAdView() {
        adView = activity.findViewById<AdView>(R.id.adViewLeftoverRecipes) as AdView
        val adRequest = AdRequest.Builder().build()
        adView!!.loadAd(adRequest)
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.leftoverRecipesList)

        listView!!.adapter = adapter //Custom adapter telling listview what to render

    }

    //Add observer to recognize model changes
    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeListLeftOverModel::class.java)

        model.getRecipeListData().observe(this, Observer<RecipeList> { list ->
            Log.d("VIEWMODEL", list!!.recipes.size.toString())

            adapter!!.updateListData()
            adapter!!.isFirstCall = false
        })
    }

    private fun startDownload() {
        searchData = arguments.getString("searchData")
        Client().getRecipes(searchData, "leftover")
    }
}