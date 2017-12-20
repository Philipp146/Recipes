package com.university.hof.philipp.recipes

import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle

import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var fab : FloatingActionButton? = null
    private var tabPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById<FloatingActionButton>(R.id.fab)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                animateFab(tab.position)
                tabPosition = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
        fab!!.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    //Setup Action Bar
    override fun onStart() {
        super.onStart()
        supportActionBar!!.setElevation(0.toFloat());
        supportActionBar!!.title = "Recipes"
    }

    private fun animateFab(tabPosition : Int){
        when (tabPosition) {
            0 -> {
                fab!!.show()
            }
            1 -> {
                fab!!.hide()
            }
            else -> {
                fab!!.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {

            when(position){
                0 -> {
                    var tab1 = Tab1LeftOvers()
                    return tab1
                }
                1 -> {
                    var tab2 = Tab2Recipes()
                    return tab2
                }
                else -> {
                    return null
                }
            }

        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }


    override fun onBackPressed() {

        if (fragmentManager.backStackEntryCount >= 1) {
            fragmentManager.popBackStack()
        }

        if(fragmentManager.backStackEntryCount == 0) {
            val tabs = findViewById<TabLayout>(R.id.tabs)
            tabs.visibility = View.VISIBLE
        }

        if(fragmentManager.backStackEntryCount == 0 && tabPosition == 0){
            fab!!.show()
        }

        super.onBackPressed()
    }
}
