package com.university.hof.philipp.recipes.Fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.university.hof.philipp.recipes.Adapter.LeftoverListViewAdapter
import com.university.hof.philipp.recipes.Controller.IngredientController
import com.university.hof.philipp.recipes.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.MainActivity




class IngredientSelection : Fragment() {

    private var searchView : SearchView? = null
    private var listAdapter : LeftoverListViewAdapter? = null
    private var listView : ListView? = null
    private var addButton : Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_leftover_selection, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hide TabLayout
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.GONE


        val ingredients = setupIngredientsToShow()
        listAdapter = LeftoverListViewAdapter(context, ingredients, true)
        listAdapter!!.setSelectedIngredients(ingredients)
        setupLayout()
        setupSearchButton()
    }

    private fun setupIngredientsToShow() : IngredientList {

        val ingredientsAll = IngredientController().getList()
        ingredientsAll.getIngredientList().sortBy { it.getName() }
        val selectedIngredients = arguments.getStringArrayList("selectedIngredients")
        var indices = arrayListOf<Int>()

        for(ingredient in ingredientsAll.getIngredientList()) {
            if (selectedIngredients.contains(ingredient.getName())) {
                ingredient.setSelected(true)
            }
        }

        return ingredientsAll
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.selection_leftovers_listview)
        listView!!.adapter = listAdapter
        listAdapter!!.notifyDataSetChanged()

        searchView = activity.findViewById<SearchView>(R.id.leftoverSelectionSearchView)
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                listAdapter!!.filter.filter(query)
                return true
            }
        })

        addButton = activity.findViewById<Button>(R.id.add_ingredients_button)
        addButton!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {

                handleAddClick()
            }
        })
    }

    private fun handleAddClick() {
        val intent = Intent(context, Tab1LeftOvers::class.java)
        intent.putExtra("selectedIngredients", listAdapter!!.getSelectedIngredients().getIngredientList())
        targetFragment.onActivityResult(targetRequestCode, RESULT_OK, intent)
        fragmentManager.popBackStack()
    }

    private fun setupSearchButton() {
        val searchButton = activity.findViewById<ImageButton>(R.id.leftoverSelectionSearchButton)
        searchButton!!.setOnClickListener { view ->
            val search = searchView!!.query.toString()
        }
    }

}
