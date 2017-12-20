package com.university.hof.philipp.recipes.Model.Ingredients

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import com.university.hof.philipp.recipes.R

/**
 * Created by philipp on 22.11.17.
 */


class Ingredient {
    private var name : String = ""
    private var img : ImageView? = null

    fun getName() : String{
        return name
    }

    fun getImg() : Int{
        return img!!.id
    }

    fun setName(str : String){
        name = str
    }

    fun setImg(newImg : Int){
        img!!.setImageResource(newImg)
    }
}


