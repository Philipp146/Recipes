package com.university.hof.philipp.recipes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.university.hof.philipp.recipes.Download.Client
import com.university.hof.philipp.recipes.Model.RecipeListSingleton
import com.university.hof.philipp.recipes.Model.Recipes.Recipe
import com.university.hof.philipp.recipes.Model.Recipes.RecipeContainer
import com.university.hof.philipp.recipes.Model.Recipes.RecipeModel
import android.support.design.widget.TabLayout
import android.webkit.WebView
import android.widget.*


/**
 * Created by patrickniepel on 06.12.17.
 */
class Details : Fragment() {

    private var adapter : MyCustomAdapter? = null

    private var listView : ListView? = null
    private var imageViewRecipe : ImageView? = null
    private var textViewPublisher : TextView? = null
    private var textViewTitle : TextView? = null

    private var detailsButton : Button? = null

    private var downloadFinished = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyCustomAdapter(context)
        setupLayout()
        setupObserver()

        //Hide TabLayout
        val tabs = activity.findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabs.visibility = View.GONE

        //Download Details for Recipe
        if (!downloadFinished) {
            var id = arguments.getString("id")
            Client().getRecipe(id)
            downloadFinished = !downloadFinished
        }
    }

    private fun setupObserver() {
        val model = ViewModelProviders.of(activity).get(RecipeModel::class.java)

        model.getRecipeData().observe(this, Observer<RecipeContainer> { list ->
            Log.d("VIEWMODEL", list!!.recipe.toString())
            adapter!!.updateListData(list)
            updateLayout()
        })
    }

    private fun updateLayout() {
        val recipeData = RecipeListSingleton.instance.recipeData

        if(!recipeData.recipe.imgUrl.isEmpty()) {
            Picasso.with(context).load(recipeData.recipe.imgUrl).fit().into(imageViewRecipe)
        }
        textViewPublisher!!.text = recipeData.recipe.publisher
        textViewTitle!!.text = recipeData.recipe.title
    }

    private fun setupLayout() {
        listView = activity.findViewById<ListView>(R.id.listViewIngredients)
        imageViewRecipe = activity.findViewById<ImageView>(R.id.imageViewRecipePicture)
        textViewPublisher = activity.findViewById<TextView>(R.id.textViewPublisherNameToFillIn)
        textViewTitle = activity.findViewById<TextView>(R.id.textViewTitleToFillIn)

        listView!!.adapter = adapter //Custom adapter telling listview what to render

        detailsButton = activity.findViewById<Button>(R.id.buttonLoadSourceUrl)
        setupDetailsListener()
    }

    private fun setupDetailsListener() {
        detailsButton!!.setOnClickListener(object: View.OnClickListener {

            override fun onClick(p0: View?) {
                loadWebView()
            }
        })
    }

    private fun loadWebView() {
        val sourceUrl = RecipeListSingleton.instance.recipeData.recipe.sourceUrl
        val web = WebView(context)
        web.loadUrl(sourceUrl)
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext : Context

        private var data : RecipeContainer = RecipeContainer(Recipe())

        init {
            this.mContext = context
        }

        //Updates the listView when the model for the leftovers owns the data after the download
        public fun updateListData(list: RecipeContainer) {
            data = RecipeListSingleton.instance.recipeData
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
    }
}