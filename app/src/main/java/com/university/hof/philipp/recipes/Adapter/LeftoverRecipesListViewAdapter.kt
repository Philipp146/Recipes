package com.university.hof.philipp.recipes.Adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import com.university.hof.philipp.recipes.Controller.NetworkConnection
import com.university.hof.philipp.recipes.Fragments.Details
import com.university.hof.philipp.recipes.Model.LeftOvers.RecipeList
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import com.university.hof.philipp.recipes.R

/**
 * Created by patrickniepel on 17.01.18.
 */
class LeftoverRecipesListViewAdapter(context: Context, activity: FragmentActivity): BaseAdapter() {

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

        /*if (this.count != 0) {
            emptyView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
        else if(!isFirstCall){
            emptyView.text = "Sorry, no recipes found"
            emptyView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }else{
            progressBar.visibility = View.VISIBLE
        }*/
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

        if (!NetworkConnection().isOnline(mContext)) {
            Toast.makeText(mContext, "Check your internet connection", Toast.LENGTH_LONG).show()
            return
        }

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