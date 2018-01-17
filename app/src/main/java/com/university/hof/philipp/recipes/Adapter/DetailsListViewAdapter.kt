package com.university.hof.philipp.recipes.Adapter

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import com.university.hof.philipp.recipes.Model.Recipes.Recipe
import com.university.hof.philipp.recipes.Model.Recipes.RecipeContainer
import com.university.hof.philipp.recipes.R

/**
 * Created by patrickniepel on 17.01.18.
 */
class DetailsListViewAdapter(context : Context, activity : FragmentActivity): BaseAdapter() {

    private val mContext : Context
    private val mActivity : FragmentActivity
    private var data : RecipeContainer = RecipeContainer(Recipe())

    var isFirstCall = true

    init {
        this.mContext = context
        this.mActivity = activity
    }

    //Updates the listView when the model for the leftovers owns the data after the download
    fun updateListData(list: RecipeContainer) {
        data = RecipeListSingleton.instance.recipeData
        toggleProgressView()
        notifyDataSetChanged()
        Log.v("UPDATE", "Ingridient List wurde geupdated")
    }

    //How many rows in list
    override fun getCount(): Int {
        return data.recipe.ingredients.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun getItem(p0: Int): Any {
        return data.recipe.ingredients[p0]
    }

    //Responsible for rendering out each row
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        /* val textView = TextView(mContext)
         textView.text = "Here is my row for my listview"
         return textView */
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.recipe_detail_row, viewGroup, false)

        val name = row.findViewById<TextView>(R.id.textViewIngredientName)
        name.text = data.recipe.ingredients[position]

        return row
    }

    private fun toggleProgressView(){
        val progressBar = mActivity.findViewById<ProgressBar>(R.id.progressBarDetails)
        val textLine = mActivity.findViewById<TextView>(R.id.lineDetails)

        if (this.count != 0) {
            textLine.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }else if(isFirstCall) {
            progressBar.visibility = View.VISIBLE
        }
    }
}