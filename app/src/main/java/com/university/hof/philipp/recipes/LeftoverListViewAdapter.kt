package com.university.hof.philipp.recipes

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList

/**
 * Created by philipp on 20.12.17.
 */
class LeftoverListViewAdapter(context: Context, data : IngredientList, editable : Boolean): BaseAdapter() {

    private var mContext : Context
    private var mEditable : Boolean? = null
    private var mData : IngredientList = IngredientList()

    init {
        mContext = context
        mData = data
        mEditable = editable
    }

    override fun getItem(p0: Int): Any {
        if (mEditable!!){
            return mData!!.getIngredient(p0)
        }
        return Ingredient()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return mData.getIngredientList().count()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.leftover_row, viewGroup, false)

        val nameTextView = row.findViewById<TextView>(R.id.leftoverItemName)
        nameTextView.text = mData.getIngredientList()[position].getName()

        val imageView = row.findViewById<ImageView>(R.id.leftoverItemImage)
        imageView.setImageResource(mData.getIngredientList()[position].getImg())

        return row
    }

}