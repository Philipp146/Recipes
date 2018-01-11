package com.university.hof.philipp.recipes.Model.Ingredients

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import com.university.hof.philipp.recipes.R

/**
 * Created by philipp on 22.11.17.
 */


class Ingredient(aName: String, aImg : Int, aIsSelected: Boolean) : Comparable<Ingredient> {

    private var name : String = ""
    private var img : Int = 0
    private var isSelected = false

    init {
        name = aName
        img = aImg
        isSelected = aIsSelected
    }

    fun getName() : String{
        return name
    }

    fun getImg() : Int {
        return img
    }

    fun setName(str : String) {
        name = str
    }

    fun setImg(newImg : Int) {
        img = newImg
    }

    fun setSelected(selected : Boolean) {
        isSelected = selected
    }

    fun getSelected() : Boolean {
        return isSelected
    }

    override fun compareTo(other: Ingredient): Int {
        return name.compareTo(other.getName())
    }
}


