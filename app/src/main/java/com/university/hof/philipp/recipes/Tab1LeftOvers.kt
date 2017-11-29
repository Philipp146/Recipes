package com.university.hof.philipp.recipes

/**
 * Created by philipp on 22.11.17.
 */

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.tab1leftovers.view.*

import com.university.hof.philipp.recipes.Download.Client
import org.w3c.dom.Text

class Tab1LeftOvers : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab1leftovers, container, false)
        Client().getData()


        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
    }

    private fun setupListView() {
        //val listView = view!!.leftovers_listView
        val listView = activity.findViewById<ListView>(R.id.leftovers_listView)

        //val grayColor = Color.parseColor("#EEEEEE")
        //listView.setBackgroundColor(grayColor)

        listView.adapter = MyCustomAdapter(context) //Custom adapter telling listview what to render
    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {

        private val mContext : Context

        private val names = arrayListOf<String>(
                "Donald Trump", "Steve Jobs", "Tom Cook"
        )

        init {
            this.mContext = context
        }

        //How many rows in list
        override fun getCount(): Int {
            return names.size
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }


        override fun getItem(p0: Int): Any {
            return names[p0]
        }

        //Responsible for rendering out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
           /* val textView = TextView(mContext)
            textView.text = "Here is my row for my listview"
            return textView */
            val layoutInflater = LayoutInflater.from(mContext)
            val row = layoutInflater.inflate(R.layout.leftover_row, viewGroup, false)

            val nameTextView = row.findViewById<TextView>(R.id.textView)
            nameTextView.text = names.get(position)

            val positionTextView = row.findViewById<TextView>(R.id.position_textview)
            positionTextView.text = "Row number: $position"

            return row
        }
    }



}