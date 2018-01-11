package com.university.hof.philipp.recipes.Fragments

/**
 * Created by philipp on 22.11.17.
 */

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.university.hof.philipp.recipes.Adapter.LeftoverListViewAdapter
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.nfc.Tag
import android.support.design.widget.TabLayout
import com.university.hof.philipp.recipes.MainActivity
import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import kotlinx.android.synthetic.main.activity_main.*


class Tab1LeftOvers : Fragment() {

    private var listAdapter : LeftoverListViewAdapter? = null
    private var listView : ListView? = null
    private var fab : FloatingActionButton? = null

    private var selectedIngredients : IngredientList = IngredientList()

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
        setupLayout()
        setupDownloadButton()
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle("Recipes")
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.leftovers_listView)
        var emptyView = activity.findViewById<TextView>(android.R.id.empty)
        listView!!.emptyView = emptyView

        fab = activity.findViewById<FloatingActionButton>(R.id.fab)
        fab!!.setOnClickListener { view ->
            fab!!.hide()
            loadIngredientSelection()
        }
    }

    private fun setupDownloadButton() {
        val downloadBtn = activity.findViewById<Button>(R.id.downloadButtonLeftovers)
        downloadBtn.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                Log.d("AUSGELÖST", "Such Button für Leftovers wurde geklickt")
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

        val downloadString = createDownloadStingForLeftovers()
        bundle.putString("searchData", downloadString)
        leftoverRecipes.arguments = bundle
        activity.supportFragmentManager.inTransaction {
            addToBackStack(LeftoverRecipes::class.java.name)
            replace(R.id.main_content, leftoverRecipes)
        }
    }

    private fun loadIngredientSelection() {

        var selectionFragment = IngredientSelection()

        var bundle = Bundle()
        var selectedValues = arrayListOf<String>()

        for (selected in selectedIngredients.getIngredientList()) {
            selectedValues.add(selected.getName())
        }

        bundle.putStringArrayList("selectedIngredients", selectedValues)
        selectionFragment.arguments = bundle

        activity.supportFragmentManager.inTransaction {

            selectionFragment.setTargetFragment(this@Tab1LeftOvers, this@Tab1LeftOvers.targetRequestCode)
            addToBackStack(IngredientSelection::class.java.name)
            replace(R.id.main_content, selectionFragment)
        }
    }

    private fun showListViewWithSelectedIngredients() {

        //Alle deselektieren, sonst wird der Haken angezeigt, da das gleiche Layout für die Row benutzt wird
        for (i in selectedIngredients.getIngredientList()) {
            i.setSelected(false)
        }


        listAdapter = LeftoverListViewAdapter(context, selectedIngredients, false)
        listView!!.adapter = listAdapter //Custom adapter telling listview what to render
    }

    private fun createDownloadStingForLeftovers() : String {
        var downloadString = ""

        for (ingredient in selectedIngredients.getIngredientList()) {
            downloadString += ingredient.getName() + ", "
        }

        return downloadString
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == targetRequestCode) {
                Log.d("ANGEKOMMEN", "" + data!!.extras["selectedIngredients"])
                val ingredients = data!!.extras["selectedIngredients"] as ArrayList<Ingredient>
                selectedIngredients.setIngredientList(ingredients)
                showListViewWithSelectedIngredients()
            }
        }

        //Show standard layout again
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.VISIBLE
        fab!!.show()
    }
/*
    private class LeftoverSelectionAdapter(context: Context): BaseAdapter() {

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
    }*/
}