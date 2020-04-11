package com.example.recipeapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.Toolbar


class AddRecipeActivity : AppCompatActivity() {

    private val sharedMediterranean = "recipeMediterranean"
    private val sharedAsian = "recipeAsian"
    private val sharedAmerican = "recipeAmerican"
    private val sharedVegan = "recipeVegan"
    private val sharedEuropean = "recipeEuropean"

    private var sharedRecipeCount: Int? = null

    private var fileUri: Uri? = null
    private var spinner: AppCompatSpinner? = null

    private var sharedPreferences: SharedPreferences? = null

    private var toolbar: Toolbar? = null

    private var submit: Button? = null
    private var imageView: ImageView? = null
    private var gallery: ImageButton? = null
    private var takePhoto: ImageButton? = null
    private var title: EditText? = null
    private var ingredients: EditText? = null
    private var steps: EditText? = null



    private var final: Uri? = null

    private val permissionCode: Int = 1000



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        val spinnerArrayTemp: MutableList<String> = ArrayList()
        spinnerArrayTemp.add("Choose Food Type")


        val stringArray =
            resources.getStringArray(R.array.recipeType)


        for ((holder) in stringArray.withIndex()) {
            spinnerArrayTemp.add(stringArray[holder].toString())
        }


        // initialize an array adapter for spinner
        val arrayAdapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerArrayTemp
        ) {

            @RequiresApi(Build.VERSION_CODES.M)
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView

                if (position == 0) {
                    view.setTextColor(resources.getColor(R.color.colorGrey,theme))
                }


                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable the first item of spinner
                return position != 0
            }
        }

        imageView = findViewById(R.id.imageView)


        spinner = findViewById(R.id.spinner_second)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = arrayAdapter


        title =  findViewById(R.id.foodTitle)
        ingredients = findViewById(R.id.ingredients)
        steps = findViewById(R.id.steps)


        gallery = findViewById(R.id.gallery)
        //listen to gallery button click
        gallery?.setOnClickListener {
            pickPhotoFromGallery()
        }

        //listen to take photo button click
        takePhoto = findViewById(R.id.takePhoto)
        takePhoto?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askCameraPermission()
            }

        }


        submit = findViewById(R.id.submit)


        //Comes From edit button to pass information(reusing AddRecipe Activity)

        val categoryEdit = intent.getIntExtra("category", 0)
        val imageEdit = intent.getStringExtra("photo")
        val titleEdit = intent.getStringExtra("TITLE")
        val ingredientEdit = intent.getStringExtra("INGREDIENT")
        val stepEdit = intent.getStringExtra("STEP")

        if (imageEdit != null){
            Log.e("opNotnull", "taknull")
            val finalEdit = Uri.parse(imageEdit)
            fileUri = finalEdit
            imageView?.setImageURI(finalEdit)
            title?.setText(titleEdit)
            ingredients?.setText(ingredientEdit)
            steps?.setText(stepEdit)
            spinner?.setSelection(categoryEdit+1)


        }else{
            //use image from media capture
            imageView?.setImageURI(fileUri)
        }


        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when (position) {
                    0 -> {
                        gallery?.isEnabled = false
                        takePhoto?.isEnabled = false


                    }
                    1 -> {

                        val image = intent.getStringExtra("photo")
                        val positionEdit = intent.getIntExtra("position", 0)
                        if (image != null){
                            final = Uri.parse(image)
                            imageView?.setImageURI(final)

                        }

                        gallery?.isEnabled = true
                        takePhoto?.isEnabled = true
                        submit?.isEnabled = true

                        sharedPreferences =
                            getSharedPreferences(sharedMediterranean, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

                        submit?.setOnClickListener {
                            if (fileUri == null){
                                Toast.makeText(parent?.context, "Please attach media", Toast.LENGTH_SHORT).show()

                            }else{
                                setResult(RESULT_OK)

                                sharedRecipeCount = sharedPreferences?.getInt("Count", 0)


                                if (final != null){
                                    editor?.putString("image$positionEdit", fileUri.toString())
                                    editor?.putString("title$positionEdit", title?.text.toString())
                                    editor?.putString("ingredient$positionEdit", ingredients?.text.toString())
                                    editor?.putString("step$positionEdit", steps?.text.toString())

                                }else{
                                    editor?.putString("image$sharedRecipeCount", fileUri.toString())
                                    editor?.putString("title$sharedRecipeCount", title?.text.toString())
                                    editor?.putString("ingredient$sharedRecipeCount", ingredients?.text.toString())
                                    editor?.putString("step$sharedRecipeCount", steps?.text.toString())

                                }



                                val temp = sharedRecipeCount?.plus(1)
                                if (temp != null && final == null) {
                                    editor?.putInt("Count", temp)
                                }
                                editor?.apply()
                                finish()
                            }


                        }
                    }
                    2 -> {

                        val image = intent.getStringExtra("photo")
                        val positionEdit = intent.getIntExtra("position", 0)
                        if (image != null){
                            final = Uri.parse(image)
                            imageView?.setImageURI(final)

                        }

                        gallery?.isEnabled = true
                        takePhoto?.isEnabled = true
                        submit?.isEnabled = true

                        sharedPreferences =
                            getSharedPreferences(sharedAsian, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

                        submit?.setOnClickListener {
                            if (fileUri == null){
                                Toast.makeText(parent?.context, "Please attach media", Toast.LENGTH_SHORT).show()

                            }else{
                                setResult(RESULT_OK)

                                sharedRecipeCount = sharedPreferences?.getInt("Count", 0)


                                if (final != null){
                                    editor?.putString("image$positionEdit", fileUri.toString())

                                }else{
                                    editor?.putString("image$sharedRecipeCount", fileUri.toString())
                                    editor?.putString("title$sharedRecipeCount", title?.text.toString())
                                    editor?.putString("ingredient$sharedRecipeCount", ingredients?.text.toString())
                                    editor?.putString("step$sharedRecipeCount", steps?.text.toString())

                                }



                                val temp = sharedRecipeCount?.plus(1)
                                if (temp != null && final == null) {
                                    editor?.putInt("Count", temp)
                                }
                                editor?.apply()
                                finish()
                            }


                        }
                    }
                    3 -> {

                        val image = intent.getStringExtra("photo")
                        val positionEdit = intent.getIntExtra("position", 0)
                        if (image != null){
                            final = Uri.parse(image)
                            imageView?.setImageURI(final)

                        }

                        gallery?.isEnabled = true
                        takePhoto?.isEnabled = true
                        submit?.isEnabled = true

                        sharedPreferences =
                            getSharedPreferences(sharedAmerican, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

                        submit?.setOnClickListener {
                            if (fileUri == null){
                                Toast.makeText(parent?.context, "Please attach media", Toast.LENGTH_SHORT).show()

                            }else{
                                setResult(RESULT_OK)

                                sharedRecipeCount = sharedPreferences?.getInt("Count", 0)


                                if (final != null){
                                    editor?.putString("image$positionEdit", fileUri.toString())

                                }else{
                                    editor?.putString("image$sharedRecipeCount", fileUri.toString())
                                    editor?.putString("title$sharedRecipeCount", title?.text.toString())
                                    editor?.putString("ingredient$sharedRecipeCount", ingredients?.text.toString())
                                    editor?.putString("step$sharedRecipeCount", steps?.text.toString())

                                }



                                val temp = sharedRecipeCount?.plus(1)
                                if (temp != null && final == null) {
                                    editor?.putInt("Count", temp)
                                }
                                editor?.apply()
                                finish()
                            }


                        }
                    }
                    4 -> {

                        val image = intent.getStringExtra("photo")
                        val positionEdit = intent.getIntExtra("position", 0)
                        if (image != null){
                            final = Uri.parse(image)
                            imageView?.setImageURI(final)

                        }

                        gallery?.isEnabled = true
                        takePhoto?.isEnabled = true
                        submit?.isEnabled = true

                        sharedPreferences =
                            getSharedPreferences(sharedVegan, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

                        submit?.setOnClickListener {
                            if (fileUri == null){
                                Toast.makeText(parent?.context, "Please attach media", Toast.LENGTH_SHORT).show()

                            }else{
                                setResult(RESULT_OK)

                                sharedRecipeCount = sharedPreferences?.getInt("Count", 0)


                                if (final != null){
                                    editor?.putString("image$positionEdit", fileUri.toString())
                                    Log.e("image0", "$sharedRecipeCount")

                                }else{
                                    editor?.putString("image$sharedRecipeCount", fileUri.toString())
                                    editor?.putString("title$sharedRecipeCount", title?.text.toString())
                                    editor?.putString("ingredient$sharedRecipeCount", ingredients?.text.toString())
                                    editor?.putString("step$sharedRecipeCount", steps?.text.toString())

                                }



                                val temp = sharedRecipeCount?.plus(1)
                                if (temp != null && final == null) {
                                    editor?.putInt("Count", temp)
                                }
                                editor?.apply()
                                finish()
                            }


                        }
                    }
                    5 ->{

                        val image = intent.getStringExtra("photo")
                        val positionEdit = intent.getIntExtra("position", 0)
                        if (image != null){
                            final = Uri.parse(image)
                            imageView?.setImageURI(final)

                        }

                        gallery?.isEnabled = true
                        takePhoto?.isEnabled = true
                        submit?.isEnabled = true

                        sharedPreferences =
                            getSharedPreferences(sharedEuropean, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

                        submit?.setOnClickListener {
                            if (fileUri == null){
                                Toast.makeText(parent?.context, "Please attach media", Toast.LENGTH_SHORT).show()

                            }else{
                                setResult(RESULT_OK)

                                sharedRecipeCount = sharedPreferences?.getInt("Count", 0)


                                if (final != null){
                                    editor?.putString("image$positionEdit", fileUri.toString())

                                }else{
                                    editor?.putString("image$sharedRecipeCount", fileUri.toString())
                                    editor?.putString("title$sharedRecipeCount", title?.text.toString())
                                    editor?.putString("ingredient$sharedRecipeCount", ingredients?.text.toString())
                                    editor?.putString("step$sharedRecipeCount", steps?.text.toString())

                                }



                                val temp = sharedRecipeCount?.plus(1)
                                if (temp != null && final == null) {
                                    editor?.putInt("Count", temp)
                                }
                                editor?.apply()
                                finish()
                            }

                        }
                    }

                }


            }

        }

        toolbar = findViewById(R.id.toolbar_add_recipe)
        setSupportActionBar(toolbar)



    }


    //pick a photo from gallery
    @SuppressLint("IntentReset")
    private fun pickPhotoFromGallery() {
        val pickImageIntent = Intent(
            Intent.ACTION_OPEN_DOCUMENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        //allow image only
        pickImageIntent.type = "image/*"

        startActivityForResult(pickImageIntent, AppConstants.PICK_PHOTO_REQUEST)
    }

    //launch the camera to take photo via intent
    private fun launchCamera() {


        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        fileUri = contentResolver
            .insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            startActivityForResult(intent, AppConstants.TAKE_PHOTO_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            permissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //ask for permission to take photo
    @RequiresApi(Build.VERSION_CODES.M)
    private fun askCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {

            val permission =
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            requestPermissions(permission, permissionCode)

        } else {
            launchCamera()
        }
    }


    //override function that is called once the photo has been taken
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK
            && requestCode == AppConstants.TAKE_PHOTO_REQUEST
        ) {
            //photo from camera
            //display the photo on the imageview

            imageView?.setImageURI(fileUri)
        } else if (resultCode == Activity.RESULT_OK
            && requestCode == AppConstants.PICK_PHOTO_REQUEST
        ) {
            //photo from gallery


            fileUri = data?.data

            imageView?.setImageURI(fileUri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
