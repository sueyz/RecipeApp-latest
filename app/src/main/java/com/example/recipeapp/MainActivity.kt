package com.example.recipeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import model.Recipes


class MainActivity : AppCompatActivity() {

    private val sharedPrefFile = "recipeTypeShared"

    private val sharedMediterranean = "recipeMediterranean"
    private val sharedAsian = "recipeAsian"
    private val sharedAmerican = "recipeAmerican"
    private val sharedVegan = "recipeVegan"
    private val sharedEuropean = "recipeEuropean"

    private var countHolder: Int = 0


    private var toolbar: Toolbar? = null
    private var spinner: AppCompatSpinner? = null
    private var floatButton: FloatingActionButton? = null

    private var edit: ImageButton? = null
    private var delete: ImageButton? = null

    private var recipes = arrayListOf<Recipes>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)



        spinner = findViewById(R.id.spinner)
        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.recipeType,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val category = sharedPreferences.getInt("Category", 0)

        spinner?.adapter = arrayAdapter
        spinner?.setSelection(category)
        floatButton = findViewById(R.id.floating_button)


        edit = findViewById(R.id.edit)
        delete = findViewById(R.id.delete)





        floatButton?.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivityForResult(intent, 1)
        }




        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(parent?.context, spinner?.get(0).toString(), Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                when (position) {
                    0 -> {

                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putInt("Category", 0)
                        editor.apply()

                        val sharedRecipesMediterranean: SharedPreferences =
                            getSharedPreferences(sharedMediterranean, Context.MODE_PRIVATE)

                        val lastCount = sharedRecipesMediterranean.getInt("Count", 0)
                        val lastCountDeletedContacts: Int


                        recipes.clear()



                        for (i in 0 until lastCount) {
                            recipes.add(
                                Recipes(
                                    0,
                                    Uri.parse(
                                        sharedRecipesMediterranean.getString(
                                            "image$i",
                                            "null"
                                        )
                                    ),
                                    sharedRecipesMediterranean.getString("title$i", "null")!!,
                                    sharedRecipesMediterranean.getString("ingredient$i", "null")!!,
                                    sharedRecipesMediterranean.getString("step$i", "null")!!
                                )
                            )

                        }

                        if (sharedRecipesMediterranean.getInt("listcount", 0) != 0) {
                            lastCountDeletedContacts =
                                sharedRecipesMediterranean.getInt("listcount", 0)

                            for (i in 1..lastCountDeletedContacts) {
                                recipes.removeAt(sharedRecipesMediterranean.getInt("list$i", 0))
                            }


                        }



                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = RecipesAdapter(
                                this@MainActivity,
                                recipes,
                                object : RecipesAdapter.ClickListener {
                                    override fun onClick(position: Int) {

                                        countHolder =
                                            sharedRecipesMediterranean.getInt("listcount", 0)
                                        countHolder += 1

                                        val editor2: SharedPreferences.Editor =
                                            sharedRecipesMediterranean.edit()
                                        editor2.putInt("list$countHolder", position)
                                        editor2.putInt("listcount", countHolder)
                                        editor2.apply()
                                    }
                                })

                        }

                    }
                    1 -> {

                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putInt("Category", 1)
                        editor.apply()

                        val sharedRecipesAsian: SharedPreferences =
                            getSharedPreferences(sharedAsian, Context.MODE_PRIVATE)

                        val lastCount = sharedRecipesAsian.getInt("Count", 0)
                        val lastCountDeletedContacts: Int

                        recipes.clear()


                        for (i in 0 until lastCount) {
                            recipes.add(
                                Recipes(
                                    1,
                                    Uri.parse(sharedRecipesAsian.getString("image$i", "null")),
                                    sharedRecipesAsian.getString("title$i", "null")!!,
                                    sharedRecipesAsian.getString("ingredient$i", "null")!!,
                                    sharedRecipesAsian.getString("step$i", "null")!!
                                )
                            )


                        }

                        if (sharedRecipesAsian.getInt("listcount", 0) != 0) {
                            lastCountDeletedContacts = sharedRecipesAsian.getInt("listcount", 0)

                            for (i in 1..lastCountDeletedContacts) {
                                recipes.removeAt(sharedRecipesAsian.getInt("list$i", 0))
                            }


                        }


                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = RecipesAdapter(
                                this@MainActivity,
                                recipes,
                                object : RecipesAdapter.ClickListener {
                                    override fun onClick(position: Int) {

                                        countHolder = sharedRecipesAsian.getInt("listcount", 0)
                                        countHolder += 1


                                        val editor2: SharedPreferences.Editor =
                                            sharedRecipesAsian.edit()
                                        editor2.putInt("list$countHolder", position)
                                        editor2.putInt("listcount", countHolder)
                                        editor2.apply()
                                    }
                                })

                        }


                    }
                    2 -> {

                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putInt("Category", 2)
                        editor.apply()

                        val sharedRecipesAmerican: SharedPreferences =
                            getSharedPreferences(sharedAmerican, Context.MODE_PRIVATE)

                        val lastCount = sharedRecipesAmerican.getInt("Count", 0)
                        val lastCountDeletedContacts: Int

                        recipes.clear()


                        for (i in 0 until lastCount) {
                            recipes.add(
                                Recipes(
                                    2,
                                    Uri.parse(sharedRecipesAmerican.getString("image$i", "null")),
                                    sharedRecipesAmerican.getString("title$i", "null")!!,
                                    sharedRecipesAmerican.getString("ingredient$i", "null")!!,
                                    sharedRecipesAmerican.getString("step$i", "null")!!
                                )
                            )


                        }

                        if (sharedRecipesAmerican.getInt("listcount", 0) != 0) {
                            lastCountDeletedContacts = sharedRecipesAmerican.getInt("listcount", 0)

                            for (i in 1..lastCountDeletedContacts) {
                                recipes.removeAt(sharedRecipesAmerican.getInt("list$i", 0))
                            }


                        }


                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = RecipesAdapter(
                                this@MainActivity,
                                recipes,
                                object : RecipesAdapter.ClickListener {
                                    override fun onClick(position: Int) {

                                        countHolder = sharedRecipesAmerican.getInt("listcount", 0)
                                        countHolder += 1


                                        val editor2: SharedPreferences.Editor =
                                            sharedRecipesAmerican.edit()
                                        editor2.putInt("list$countHolder", position)
                                        editor2.putInt("listcount", countHolder)
                                        editor2.apply()
                                    }
                                })

                        }


                    }
                    3 -> {

                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putInt("Category", 3)
                        editor.apply()

                        val sharedRecipesVegan: SharedPreferences =
                            getSharedPreferences(sharedVegan, Context.MODE_PRIVATE)

                        val lastCount = sharedRecipesVegan.getInt("Count", 0)
                        val lastCountDeletedContacts: Int

                        recipes.clear()


                        for (i in 0 until lastCount) {
                            recipes.add(
                                Recipes(
                                    3,
                                    Uri.parse(sharedRecipesVegan.getString("image$i", "null")),
                                    sharedRecipesVegan.getString("title$i", "null")!!,
                                    sharedRecipesVegan.getString("ingredient$i", "null")!!,
                                    sharedRecipesVegan.getString("step$i", "null")!!
                                )
                            )


                        }

                        if (sharedRecipesVegan.getInt("listcount", 0) != 0) {
                            lastCountDeletedContacts = sharedRecipesVegan.getInt("listcount", 0)

                            for (i in 1..lastCountDeletedContacts) {
                                recipes.removeAt(sharedRecipesVegan.getInt("list$i", 0))
                            }


                        }


                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = RecipesAdapter(
                                this@MainActivity,
                                recipes,
                                object : RecipesAdapter.ClickListener {
                                    override fun onClick(position: Int) {

                                        countHolder = sharedRecipesVegan.getInt("listcount", 0)
                                        countHolder += 1


                                        val editor2: SharedPreferences.Editor =
                                            sharedRecipesVegan.edit()
                                        editor2.putInt("list$countHolder", position)
                                        editor2.putInt("listcount", countHolder)
                                        editor2.apply()
                                    }
                                })

                        }


                    }
                    4 -> {

                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putInt("Category", 4)
                        editor.apply()

                        val sharedRecipesEuropean: SharedPreferences =
                            getSharedPreferences(sharedEuropean, Context.MODE_PRIVATE)

                        val lastCount = sharedRecipesEuropean.getInt("Count", 0)
                        val lastCountDeletedContacts: Int

                        recipes.clear()


                        for (i in 0 until lastCount) {
                            recipes.add(
                                Recipes(
                                    4,
                                    Uri.parse(sharedRecipesEuropean.getString("image$i", "null")),
                                    sharedRecipesEuropean.getString("title$i", "null")!!,
                                    sharedRecipesEuropean.getString("ingredient$i", "null")!!,
                                    sharedRecipesEuropean.getString("step$i", "null")!!
                                )
                            )


                        }

                        if (sharedRecipesEuropean.getInt("listcount", 0) != 0) {
                            lastCountDeletedContacts = sharedRecipesEuropean.getInt("listcount", 0)

                            for (i in 1..lastCountDeletedContacts) {
                                recipes.removeAt(sharedRecipesEuropean.getInt("list$i", 0))
                            }


                        }


                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = RecipesAdapter(
                                this@MainActivity,
                                recipes,
                                object : RecipesAdapter.ClickListener {
                                    override fun onClick(position: Int) {

                                        countHolder = sharedRecipesEuropean.getInt("listcount", 0)
                                        countHolder += 1


                                        val editor2: SharedPreferences.Editor =
                                            sharedRecipesEuropean.edit()
                                        editor2.putInt("list$countHolder", position)
                                        editor2.putInt("listcount", countHolder)
                                        editor2.apply()
                                    }
                                })

                        }

                    }

                }

            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            recreate()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
