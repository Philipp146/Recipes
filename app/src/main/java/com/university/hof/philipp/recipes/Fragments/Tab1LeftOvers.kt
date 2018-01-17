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
import com.university.hof.philipp.recipes.Adapter.LeftoverSelectionListViewAdapter
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.support.design.widget.TabLayout
import android.view.MenuItem
import com.university.hof.philipp.recipes.Adapter.Tab1ListViewAdapter
import com.university.hof.philipp.recipes.MainActivity
import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient


class Tab1LeftOvers : Fragment() {

    private var listAdapter : Tab1ListViewAdapter? = null
    private var listView : ListView? = null
    private var fab : FloatingActionButton? = null
    private var emptyView : TextView? = null

    private var selectedLeftovers: IngredientList = IngredientList()

    private var selectedIngredientsToLoad : IngredientList = IngredientList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab1leftovers, container, false)
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
        mainActivity.supportActionBar!!.setTitle("Recipes")
        Log.d("RESUME", "Aufgerufen")

        if (selectedLeftovers.getIngredientList().size != 0) {
            emptyView!!.visibility = View.GONE
        }
        else {
            emptyView!!.visibility = View.VISIBLE
        }
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.leftovers_listView)
        emptyView = activity.findViewById<TextView>(R.id.empty_view_tab_1)

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

        var selectionFragment = LeftoverSelection()

        var bundle = Bundle()
        var selectedValues = arrayListOf<String>()

        for (selected in selectedLeftovers.getIngredientList()) {
            selectedValues.add(selected.getName())
        }

        bundle.putStringArrayList("selectedLeftovers", selectedValues)
        selectionFragment.arguments = bundle

        activity.supportFragmentManager.inTransaction {

            selectionFragment.setTargetFragment(this@Tab1LeftOvers, this@Tab1LeftOvers.targetRequestCode)
            addToBackStack(LeftoverSelection::class.java.name)
            replace(R.id.main_content, selectionFragment)
        }
    }

    private fun showListViewWithSelectedIngredients() {

        //Alle deselektieren, sonst wird der Haken angezeigt, da das gleiche Layout für die Row benutzt wird
        for (i in selectedLeftovers.getIngredientList()) {
            i.setSelected(false)
        }

        listAdapter = Tab1ListViewAdapter(context, selectedLeftovers)
        listView!!.adapter = listAdapter //Custom adapter telling listview what to render
    }

    private fun createDownloadStingForLeftovers() : String {
        var downloadString = ""

        if (listAdapter != null) {
            for (ingredient in listAdapter!!.getSelectedIngredients().getIngredientList()) {
                downloadString += ingredient.getName() + ", "
            }
        }



        return downloadString
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == targetRequestCode) {
                Log.d("ANGEKOMMEN", "" + data!!.extras["selectedLeftovers"])
                val ingredients = data!!.extras["selectedLeftovers"] as ArrayList<Ingredient>
                selectedLeftovers.setIngredientList(ingredients)
                showListViewWithSelectedIngredients()

                toggleEmptyView()
            }
        }

        //Show standard layout again
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.VISIBLE
        fab!!.show()
    }

    private fun toggleEmptyView() {
        if (selectedLeftovers.getIngredientList().size != 0) {
            emptyView!!.visibility = View.GONE
        }
        else {
            emptyView!!.visibility = View.VISIBLE
        }
    }
}