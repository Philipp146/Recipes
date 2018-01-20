package com.university.hof.philipp.recipes.Adapter

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.R

/**
 * Created by patrickniepel on 17.01.18.
 */
class Tab1ListViewAdapter(context: Context, data : IngredientList, activity: FragmentActivity): BaseAdapter() {

    private var mActivity : FragmentActivity
    private var mContext : Context
    private var mData: IngredientList = IngredientList()
    private var selectedIngredients = IngredientList()

    init {
        mContext = context
        mData = data
        mActivity = activity
    }

    override fun getItem(p0: Int): Any {
        return mData.getIngredient(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return mData.getIngredientList().size
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.leftover_row, viewGroup, false)
        val ingredient = mData.getIngredientList()[position]

        val nameTextView = row.findViewById<TextView>(R.id.leftoverItemName)
        nameTextView.text = ingredient.getName()

        val imageView = row.findViewById<ImageView>(R.id.leftoverItemImage)
        imageView.setImageResource(ingredient.getImg())

        val selectedImage = row.findViewById<ImageView>(R.id.is_selected_image)

        if(ingredient.getSelected()) {
            selectedImage.setImageResource(R.drawable.checked)
        }
        else {
            selectedImage.setImageDrawable(null)
        }

        //Wenn man selektieren kann, kann auf ein Ingredient geklickt werden. Dies wird dann einer Liste hinzugefügt, die beim Schließen des Screens an das vorherige Fragment weitergegeben wird
        row.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v : View){
                val selectedIngredient = mData.getIngredientList()[position]
                selectedIngredient.setSelected(!selectedIngredient.getSelected())
                handleSelection(selectedIngredient)
                notifyDataSetChanged()
                Log.v("###################", "" + mData.getIngredientList().size)
            }
        })

        row.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                Log.v("###################", "ONLONGCLICK")
                val adb= AlertDialog.Builder(mContext)
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + mData.getIngredient(position).getName());
                adb.setNegativeButton(mActivity.getString(R.string.cancel), null);
                adb.setPositiveButton("Ok", { dialog, whichButton ->
                    val ingredientToRemove = mData.getIngredient(position)
                    mData.removeIngredient(position)
                    selectedIngredients.removeIngredient(ingredientToRemove)
                    notifyDataSetChanged()

                    if (mData.getIngredientList().isEmpty()) {
                        val emptyView = mActivity.findViewById<TextView>(R.id.empty_view_tab_1)
                        emptyView.visibility = View.VISIBLE
                    }
                })
                adb.show()
                return true
            }
        })

        return row
    }

    fun setSelectedLeftovers(list : IngredientList) {
        for (i in list.getIngredientList()) {
            if(i.getSelected()) {
                selectedIngredients.addIngredient(i)
            }
        }
    }

    private fun handleSelection(ingredient : Ingredient) {

        if(!selectedIngredients.getIngredientList().contains(ingredient)) {
            selectedIngredients.addIngredient(ingredient)
        }
        //Schon vorhanden
        else {
            val index = selectedIngredients.getIngredientList().indexOf(ingredient)
            selectedIngredients.removeIngredient(index)
        }
    }

    fun getSelectedIngredients() : IngredientList {
        return selectedIngredients
    }
}