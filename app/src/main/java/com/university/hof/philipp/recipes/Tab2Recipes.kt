package com.university.hof.philipp.recipes

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.view.LayoutInflater

/**
 * Created by philipp on 22.11.17.
 */
class Tab2Recipes : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab2recipes, container, false)
        return rootView
    }


}