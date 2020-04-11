package com.example.recipeapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recipe_view.*

class RecipeDetails: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_view)

        val image = intent.getStringExtra("photo")
        val final = Uri.parse(image)

        val title = intent.getStringExtra("title")
        val ingredient = intent.getStringExtra("ingredient")
        val step = intent.getStringExtra("step")


        photo.setImageURI(final)
        recipe_title.text = title
        recipe_ingredient.text = ingredient
        recipe_step.text = step

    }
}