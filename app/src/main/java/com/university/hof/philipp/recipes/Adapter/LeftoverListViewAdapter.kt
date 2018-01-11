package com.university.hof.philipp.recipes.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.R

/**
 * Created by philipp on 20.12.17.
 */
class LeftoverListViewAdapter(context: Context, data : IngredientList, editable : Boolean): BaseAdapter(), Filterable {

    private var mContext : Context
    private var mEditable : Boolean
    private var mDataAll : IngredientList = IngredientList()
    private var mDataFiltered : IngredientList = IngredientList()
    private var selectedIngredients = IngredientList()

    init {
        mContext = context
        mDataAll = data
        mDataFiltered = mDataAll
        mEditable = editable
        Log.d("LeftoverListViewAdapter", "Größe der IngredientList " + mDataFiltered.getIngredientList().size)
    }

    override fun getItem(p0: Int): Any {
        /*if (mEditable){
            return mData.getIngredient(p0)
        }*/
        return mDataFiltered.getIngredient(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return mDataFiltered.getIngredientList().size
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.leftover_row, viewGroup, false)
        val ingredient = mDataFiltered.getIngredientList()[position]

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
        if (mEditable) {
            row.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v : View){
                    val selectedIngredient = mDataFiltered.getIngredientList()[position]
                    selectedIngredient.setSelected(!selectedIngredient.getSelected())
                    handleSelection(selectedIngredient)
                    notifyDataSetChanged()
                }
            })
        }

        return row
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

    fun setSelectedIngredients(list : IngredientList) {
        for (i in list.getIngredientList()) {
            if(i.getSelected()) {
                selectedIngredients.addIngredient(i)
            }
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun publishResults(constraint: CharSequence, results: FilterResults) {

                val values = results.values as List<String>

                mDataFiltered = IngredientList()

                for (i in mDataAll.getIngredientList()) {
                    if (values.contains(i.getName())) {
                        mDataFiltered.addIngredient(i)
                    }
                }
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var constraint = constraint

                val results = FilterResults()
                val FilteredArrayNames = ArrayList<String>()

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase()
                for (i in 0 until mDataAll.getIngredientList().size) {
                    val dataNames = mDataAll.getIngredientList()[i].getName()
                    if (dataNames.toLowerCase().startsWith(constraint.toString())) {
                        FilteredArrayNames.add(dataNames)
                    }
                }

                results.count = FilteredArrayNames.size
                results.values = FilteredArrayNames
                Log.e("VALUES", results.values.toString())

                return results
            }
        }
    }

}