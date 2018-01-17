package com.university.hof.philipp.recipes.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.university.hof.philipp.recipes.Adapter.Tab2ListViewAdapter
import com.university.hof.philipp.recipes.Controller.NetworkConnection
import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.MainActivity
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.Recipes.RecipeListModel
import com.university.hof.philipp.recipes.R


/**
 * Created by philipp on 22.11.17.
 */
class Tab2Recipes : Fragment() {

    private var adapter : Tab2ListViewAdapter? = null
    private var listView : ListView? = null
    private var searchView : SearchView? = null
    private var emptyView : TextView? = null
    private var progressBar : ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab2recipes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Tab2ListViewAdapter(context, activity)

        progressBar = activity.findViewById<ProgressBar>(R.id.progressBarTab2)
        progressBar!!.visibility = View.GONE

        setupLayout()
        setupObserver()
        setupDownloadButton()
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle("Recipes")
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.recipe_listView)
        listView!!.adapter = adapter //Custom adapter telling listview what to render

        emptyView = activity.findViewById<TextView>(R.id.empty_view_tab_2)
        //emptyView!!.visibility = View.VISIBLE
        //listView!!.emptyView = emptyView

        searchView = activity.findViewById<SearchView>(R.id.searchViewRecipe)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                val search = searchView!!.query.toString()
                Client().getRecipes(search, "recipe")
                val inputManager : InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(if (null == activity.currentFocus) null else activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //listAdapter!!.filter.filter(query)
                return true
            }
        })
    }

    //Add observer to recognize model changes
    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeListModel::class.java)

        model.getRecipeListData().observe(this, Observer<RecipeList> { list ->
            adapter!!.updateListData()
            adapter!!.isFirstCall = false
        })

    }

    private fun setupDownloadButton() {
        val downloadBtn = activity.findViewById<Button>(R.id.downloadButtonRecipe)
        downloadBtn.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {


                if (!NetworkConnection().isOnline(context)) {
                    Toast.makeText(view!!.context, "Check your internet connection", Toast.LENGTH_LONG).show()
                    return
                }

                emptyView!!.visibility = View.GONE
                progressBar!!.visibility = View.VISIBLE
                //downloadRecipes for search fields
                val search = searchView!!.query.toString()
                Client().getRecipes(search, "recipe")
                val inputManager : InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(if (null == activity.currentFocus) null else activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }
}