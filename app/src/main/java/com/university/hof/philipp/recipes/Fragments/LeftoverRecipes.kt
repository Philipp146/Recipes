package com.university.hof.philipp.recipes.Fragments

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
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

    private var adapter : LeftoverRecipesAdapter? = null
    private var listView : ListView? = null
    private var searchData : String = ""
    private var emptyView : TextView? = null
    private var progressBar : ProgressBar? = null

    private var called = false

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

        adapter = LeftoverRecipesAdapter(context, activity)

        setupLayout()
        setupObserver()

        //Download ist bereits erfolgt wenn true
        if (!called) {
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
        if (!called) {
            progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
            progressBar!!.visibility = View.VISIBLE
        }

        called = true
    }

    override fun onDetach() {
        super.onDetach()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle("Recipes")
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


    private class LeftoverRecipesAdapter(context: Context, activity: FragmentActivity): BaseAdapter() {

        private val mContext : Context
        private val mActivity : FragmentActivity
        var isFirstCall = true

        private var data : RecipeList = RecipeList(mutableListOf())

        init {
            this.mContext = context
            this.mActivity = activity
        }

        //Updates the listView when the recipe model owns the new data after the download
        fun updateListData() {
            data = RecipeListSingleton.instance.recipeListLeftOverData
            toggleEmptyView()
            notifyDataSetChanged()
        }

        //How many rows in list
        override fun getCount(): Int {
            return data.recipes.size
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getItem(p0: Int): Any {
            return data.recipes[p0]
        }

        //Responsible for rendering out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)

            val row = layoutInflater.inflate(R.layout.recipe_row, viewGroup, false)

            row.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v : View){
                    getRecipe(data.recipes[position].id)
                }
            })

            setupView(row, position)

            return row
        }

        private fun toggleEmptyView() {
            val emptyView = mActivity.findViewById<TextView>(R.id.empty_view_leftovers)
            val progressBar = mActivity.findViewById<ProgressBar>(R.id.progressBar)

            if (this.count != 0) {
                emptyView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            else if(!isFirstCall){
                emptyView.text = "Sorry, no recipes found"
                emptyView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

        private fun setupView(row : View, position : Int) {

            val nameTextView = row.findViewById<TextView>(R.id.recipeName)
            nameTextView.text = data.recipes[position].title

            val positionTextView = row.findViewById<TextView>(R.id.recipeInfo)
            val rank = data.recipes[position].sRank.toInt().toString()
            positionTextView.text = rank

            val publisherNameTextView = row.findViewById<TextView>(R.id.textViewPublisherName)
            val publisherName = data.recipes[position].publisherName
            publisherNameTextView.text = publisherName

            val recipeImage = row.findViewById<ImageView>(R.id.recipeImage)
            val imgUrl = data.recipes[position].imgUrl

            Picasso.with(mContext).load(imgUrl).fit().into(recipeImage)

        }

        fun getRecipe(recipeId: String){
            // recipe-Id example: recipeId = "0063b5"
            startDetailScreen(recipeId)
            //Start new View for
            Log.v("Listener", "Funzt, ID = " + recipeId)
        }

        inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
            val fragmentTransaction = beginTransaction()
            fragmentTransaction.func()
            fragmentTransaction.commit()
        }

        private fun startDetailScreen(id : String) {
            var details = Details()

            var bundle = Bundle()
            bundle.putString("id", id)
            details.arguments = bundle
            mActivity.supportFragmentManager.inTransaction {
                addToBackStack(Details::class.java.name)
                replace(R.id.main_content, details)
            }
        }
    }
}