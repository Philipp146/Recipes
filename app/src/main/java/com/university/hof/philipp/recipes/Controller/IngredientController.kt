package com.university.hof.philipp.recipes.Controller

import com.university.hof.philipp.recipes.Model.Ingredients.Ingredient
import com.university.hof.philipp.recipes.Model.Ingredients.IngredientList
import com.university.hof.philipp.recipes.R

/**
 * Created by philipp on 20.12.17.
 */
class IngredientController {

    private var ingredientList = IngredientList()
    private var ingredientsNames = arrayListOf<String>("Apple", "Aubergine", "Banana", "Broccoli", "Carrot", "Cherries", "Chicken", "Coffee", "Eggs", "Fish", "Grain", "Ketchup",
            "Lemon", "Meat", "Milk", "Mustard", "Noodles", "Onion", "Orange", "Pear", "Pineapple", "Potatoes", "Radish", "Raspberry", "Rice", "Salad", "Sausage", "Strawberry",
            "Tea", "Toast", "Tomato", "Water")

    init {
        createIngredients()
    }

    fun getList() : IngredientList {
        return ingredientList
    }

    private fun createIngredients() {

        for (i in ingredientsNames) {

            when(i) {

                "Apple" -> ingredientList.addIngredient(Ingredient(i, R.drawable.apple, false))
                "Aubergine" -> ingredientList.addIngredient(Ingredient(i, R.drawable.aubergine, false))
                "Banana" -> ingredientList.addIngredient(Ingredient(i, R.drawable.banana, false))
                "Broccoli" -> ingredientList.addIngredient(Ingredient(i, R.drawable.broccoli, false))
                "Carrot" -> ingredientList.addIngredient(Ingredient(i, R.drawable.carrot, false))
                "Cherries" -> ingredientList.addIngredient(Ingredient(i, R.drawable.cherries, false))
                "Chicken" -> ingredientList.addIngredient(Ingredient(i, R.drawable.chicken, false))
                "Coffee" -> ingredientList.addIngredient(Ingredient(i, R.drawable.coffee, false))
                "Eggs" -> ingredientList.addIngredient(Ingredient(i, R.drawable.eggs, false))
                "Fish" -> ingredientList.addIngredient(Ingredient(i, R.drawable.fish, false))
                "Grain" -> ingredientList.addIngredient(Ingredient(i, R.drawable.grain, false))
                "Ketchup" -> ingredientList.addIngredient(Ingredient(i, R.drawable.ketchup, false))
                "Lemon" -> ingredientList.addIngredient(Ingredient(i, R.drawable.lemon, false))
                "Meat" -> ingredientList.addIngredient(Ingredient(i, R.drawable.meat, false))
                "Milk" -> ingredientList.addIngredient(Ingredient(i, R.drawable.milk, false))
                "Mustard" -> ingredientList.addIngredient(Ingredient(i, R.drawable.mustard, false))
                "Noodles" -> ingredientList.addIngredient(Ingredient(i, R.drawable.noodles, false))
                "Onion" -> ingredientList.addIngredient(Ingredient(i, R.drawable.onion, false))
                "Orange" -> ingredientList.addIngredient(Ingredient(i, R.drawable.orange, false))
                "Pear" -> ingredientList.addIngredient(Ingredient(i, R.drawable.pear, false))
                "Pineapple" -> ingredientList.addIngredient(Ingredient(i, R.drawable.pineapple, false))
                "Potatoes" -> ingredientList.addIngredient(Ingredient(i, R.drawable.potatoes, false))
                "Radish" -> ingredientList.addIngredient(Ingredient(i, R.drawable.radish, false))
                "Raspberry" -> ingredientList.addIngredient(Ingredient(i, R.drawable.raspberry, false))
                "Rice" -> ingredientList.addIngredient(Ingredient(i, R.drawable.rice, false))
                "Salad" -> ingredientList.addIngredient(Ingredient(i, R.drawable.salad, false))
                "Sausage" -> ingredientList.addIngredient(Ingredient(i, R.drawable.sausage, false))
                "Strawberry" -> ingredientList.addIngredient(Ingredient(i, R.drawable.strawberry, false))
                "Tea" -> ingredientList.addIngredient(Ingredient(i, R.drawable.tea, false))
                "Toast" -> ingredientList.addIngredient(Ingredient(i, R.drawable.toast, false))
                "Tomato" -> ingredientList.addIngredient(Ingredient(i, R.drawable.tomato, false))
                "Water" -> ingredientList.addIngredient(Ingredient(i, R.drawable.water, false))

            }
        }
    }
}