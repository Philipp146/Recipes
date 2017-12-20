package com.university.hof.philipp.recipes

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class IngredientSelection : Fragment() {

    private var searchView : SearchView? = null
    private var listAdapter : LeftoverListViewAdapter? = null
    private var listView : ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_leftover_selection, container, false)
        val ingredients = IngredientController().getList()
        listAdapter = LeftoverListViewAdapter(context, ingredients, true)
        setupLayout()
        setupSearchButton()
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.leftovers_listView)
        //listView!!.adapter = listAdapter

        searchView = activity.findViewById<SearchView>(R.id.leftoverSelectionSearchView)
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                listView!!.setFilterText(newText)
                return true
            }
        })
    }

    private fun setupSearchButton() {
        val searchButton = activity.findViewById<ImageButton>(R.id.leftoverSelectionSearchButton)
        searchButton!!.setOnClickListener { view ->
            val search = searchView!!.query.toString()
        }
    }


}
