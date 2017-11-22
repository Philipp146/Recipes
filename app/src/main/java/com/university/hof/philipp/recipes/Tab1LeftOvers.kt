package com.university.hof.philipp.recipes

/**
 * Created by philipp on 22.11.17.
 */

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.view.LayoutInflater

class Tab1LeftOvers : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab1leftovers, container, false)
        return rootView
    }



}