package com.university.hof.philipp.recipes.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.squareup.picasso.Picasso
import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.Recipes.RecipeListModel
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import com.university.hof.philipp.recipes.R


/**
 * Created by philipp on 22.11.17.
 */
class Tab2Recipes : Fragment() {

    private var adapter : RecipesAdapter? = null
    private var listView : ListView? = null
    private var searchView : SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab2recipes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipesAdapter(context, activity)

        setupLayout()
        setupObserver()
        setupDownloadButton()
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.recipe_listView)
        listView!!.adapter = adapter //Custom adapter telling listview what to render

        searchView = activity.findViewById<SearchView>(R.id.searchViewRecipe)
    }

    //Add observer to recognize model changes
    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeListModel::class.java)

        model.getRecipeListData().observe(this, Observer<RecipeList> { list ->
            Log.d("VIEWMODEL", list!!.recipes.size.toString())
            adapter!!.updateListData()
        })
    }

    private fun setupDownloadButton() {
        val downloadBtn = activity.findViewById<Button>(R.id.downloadButtonRecipe)
        downloadBtn.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {

                //downloadRecipes for search fields
                val search = searchView!!.query.toString()
                Client().getRecipes(search, "recipe")
                val inputManager : InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(if (null == activity.currentFocus) null else activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }


    private class RecipesAdapter(context: Context, activity: FragmentActivity): BaseAdapter() {

        private val mContext : Context
        private val mActivity : FragmentActivity

        private var data : RecipeList = RecipeList(mutableListOf())

        init {
            this.mContext = context
            this.mActivity = activity
        }

        //Updates the listView when the recipe model owns the new data after the download
        public fun updateListData() {
            data = RecipeListSingleton.instance.recipeListData
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

        private fun setupView(row : View, position : Int) {

            val nameTextView = row.findViewById<TextView>(R.id.recipeName)
            nameTextView.text = data.recipes[position].title

            val positionTextView = row.findViewById<TextView>(R.id.recipeInfo)
            val rank = data.recipes[position].sRank.toInt().toString()
            positionTextView.text = "Rating: " + rank

            val recipeImage = row.findViewById<ImageView>(R.id.recipeImage)
            val imgUrl = data.recipes[position].imgUrl

            Picasso.with(mContext).load(imgUrl).fit().into(recipeImage);
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