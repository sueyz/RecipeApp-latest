package model

import android.net.Uri

data class Recipes(
    val category : Int,
    val photoUri : Uri,
    val title : String,
    val ingredient : String,
    val step : String

)