package com.university.hof.philipp.recipes.Fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.university.hof.philipp.recipes.Adapter.LeftoverSelectionListViewAdapter
import com.university.hof.philipp.recipes.Controller.IngredientController
import com.university.hof.philipp.recipes.R
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.MainActivity
import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient

class LeftoverSelection : Fragment() {

    private var searchView : SearchView? = null
    private var listAdapter : LeftoverSelectionListViewAdapter? = null
    private var listView : ListView? = null
    private var addButton : Button? = null
    private var customButton : Button? = null

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


        val leftovers = setupIngredientsToShow()
        listAdapter = LeftoverSelectionListViewAdapter(context, leftovers)
        listAdapter!!.setSelectedLeftovers(leftovers)
        setupLayout()
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.getSupportActionBar()!!.setTitle(getString(R.string.leftover_selection))
    }

    // Handles the correct displaying of the leftovers (selected, unselected, custom)
    private fun setupIngredientsToShow() : IngredientList {

        val leftoversAll = IngredientController().getList()
        var customLeftovers = ArrayList<Ingredient>()
        val selectedIngredients = arguments.getStringArrayList("selectedLeftovers")
        Log.v("#############", "ingri " + selectedIngredients)

        for(leftover in leftoversAll.getIngredientList()) {
            if (selectedIngredients.contains(leftover.getName())) {
                leftover.setSelected(true)
            }

            for(selectedName in selectedIngredients) {
                if (leftover.getName() != selectedName && customLeftovers.all { it.getName() != selectedName } && leftoversAll.getIngredientList().all { it.getName() != selectedName }) {
                    customLeftovers.add(IngredientController().getCustomIngredient(selectedName))
                }
            }
        }

        leftoversAll.addIngredients(customLeftovers)

        leftoversAll.getIngredientList().sortBy { it.getName() }

        return leftoversAll
    }

    override fun onDetach() {
        super.onDetach()
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.setTitle(getString(R.string.recipes))
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

        addButton = activity.findViewById<Button>(R.id.add_leftovers_button)
        addButton!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {

                handleAddClick()
            }
        })

        customButton = activity.findViewById<Button>(R.id.leftoverSelectionAddCustom)
        customButton!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {

                addCustomIngredient()
            }
        })
    }

    private fun handleAddClick() {
        val intent = Intent(context, Tab1LeftOvers::class.java)
        intent.putExtra("selectedLeftovers", listAdapter!!.getSelectedLeftovers().getIngredientList())
        targetFragment.onActivityResult(targetRequestCode, RESULT_OK, intent)
        fragmentManager.popBackStack()
    }

    private fun addCustomIngredient() {
        val customName = searchView!!.query.toString()

        //Wenn nichts eingegeben wurde, wird auch nichts in die Liste hinzugefügt
        if (customName.isEmpty()) {
            return
        }

        //Hide keyboard
        val inputManager : InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(if (null == activity.currentFocus) null else activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        val customIngredient = IngredientController().getCustomIngredient(customName)

        if (listAdapter!!.getSelectedLeftovers().getIngredientList().any { it.getName().equals(customIngredient.getName()) }) {
            return
        }

        listAdapter!!.addCustomIngredient(customIngredient)
    }
}
