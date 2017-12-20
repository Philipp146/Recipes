package com.university.hof.philipp.recipes

/**
 * Created by philipp on 22.11.17.
 */

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

import android.support.design.widget.TabLayout

import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.widget.*

import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeListLeftOverModel
import com.university.hof.philipp.recipes.Model.RecipeListSingleton

class Tab1LeftOvers : Fragment() {

    private var adapter : MyCustomAdapter? = null
    private var listView : ListView? = null
    private var fab : FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab1leftovers, container, false)
        //floatingButton.setOnClickListener {
            //loadIngredientSelection()
        //}


        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyCustomAdapter(context)
        setupLayout()
        setupObserver()
        setupDownloadButton()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(activity.applicationContext, "Resumed", Toast.LENGTH_SHORT)
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.leftovers_listView)
        listView!!.adapter = adapter //Custom adapter telling listview what to render
        fab = activity.findViewById<FloatingActionButton>(R.id.fab)
        fab!!.setOnClickListener { view ->
            loadIngredientSelection()
            fab!!.hide()
        }
    }

    //Adds a observer to recognize model changes
    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeListLeftOverModel::class.java)

        model.getRecipeListData().observe(this, Observer<RecipeList> { list ->
            Log.d("VIEWMODEL", list!!.recipes.size.toString())
            adapter!!.updateListData(list)
        })
    }

    private fun setupDownloadButton() {
        val downloadBtn = activity.findViewById<Button>(R.id.downloadButtonLeftovers)
        downloadBtn.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                fab!!.hide()
                loadLeftOverRecipesFragment()
            }
        })
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    private fun loadLeftOverRecipesFragment() {
        var leftoverRecipes = LeftoverRecipes()

        var bundle = Bundle()

        //TODO: Hier Muss noch die liste mit ausgewählten lebensmittel reingeladen werden
        bundle.putString("searchData", "tomato, cucumber")
        leftoverRecipes.arguments = bundle
        activity.supportFragmentManager.inTransaction {
            addToBackStack(LeftoverRecipes::class.java.name)
            replace(R.id.main_content, leftoverRecipes)
        }
    }

    private fun loadIngredientSelection() {
        activity.supportFragmentManager.inTransaction {
            addToBackStack(IngredientSelection::class.java.name)
            replace(R.id.main_content, IngredientSelection())
        }
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext : Context

        private var data : RecipeList = RecipeList(mutableListOf())

        init {
            this.mContext = context
        }

        //Updates the listView when the model for the leftovers owns the data after the download
        public fun updateListData(list: RecipeList) {
            data = RecipeListSingleton.instance.recipeListLeftOverData
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
           /* val textView = TextView(mContext)
            textView.text = "Here is my row for my listview"
            return textView */
            val layoutInflater = LayoutInflater.from(mContext)
            val row = layoutInflater.inflate(R.layout.leftover_row, viewGroup, false)

            val nameTextView = row.findViewById<TextView>(R.id.leftoverItemName)
            nameTextView.text = data.recipes[position].title


            return row
        }
    }
}