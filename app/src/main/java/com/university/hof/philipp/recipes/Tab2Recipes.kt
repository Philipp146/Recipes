package com.university.hof.philipp.recipes

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.Model.RecipeList
import com.university.hof.philipp.recipes.Model.RecipeListSingleton

/**
 * Created by philipp on 22.11.17.
 */
class Tab2Recipes : Fragment() {

    private var adapter : MyCustomAdapter? = null
    private var listView : ListView? = null
    private var searchView : SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab2recipes, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyCustomAdapter(context)
        setupLayout()
        setupDownloadButton()
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.recipe_listView)
        listView!!.adapter = adapter //Custom adapter telling listview what to render

        searchView = activity.findViewById<SearchView>(R.id.searchViewRecipe)
    }

    private fun setupDownloadButton() {
        val downloadBtn = activity.findViewById<Button>(R.id.downloadButtonRecipe)
        downloadBtn.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {

                //downloadRecipes for search fields
                val search = searchView!!.query.toString()
                Client().getRecipes(search)

                //delegate download to adapter
                adapter!!.updateListData()
                adapter!!.notifyDataSetChanged() //Notify adapter that data was changed
            }
        })
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext : Context

        private var data : RecipeList = RecipeList(mutableListOf())

        init {
            this.mContext = context
        }

        public fun updateListData() {
            data = RecipeListSingleton.instance.recipeListData
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

            val nameTextView = row.findViewById<TextView>(R.id.recipeName)
            nameTextView.text = data.recipes[position].title

            val positionTextView = row.findViewById<TextView>(R.id.recipeInfo)
            val rank = data.recipes[position].sRank.toInt().toString()
            positionTextView.text = rank

            return row
        }
    }
}