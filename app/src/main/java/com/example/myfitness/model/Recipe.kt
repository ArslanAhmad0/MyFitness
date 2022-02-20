package com.example.myfitness.model

data class MainRecipe(
val uri:String,
val label:String,
val images:Images,
val ingredients:List<Ingredients>,
val source:String,
val calories:Double,
val url:String,
val shareAs:String,
val image:String
)
data class Ingredients(
    val text:String,
    val quantity:Double,
    val measure:String,
    val food:String,
    val weight:Double,
    val foodCategory:String,
    val foodId:String,
    val image:String
)

data class Images(
    val sMALL:SmallImg,
    val rEGULAR:RegularImg,
    val lARGE:LargeImg
)
data class SmallImg(
    val url:String,
    val width:Int
)
data class RegularImg(
    val url:String,
    val width:Int
)
data class LargeImg(
     val url:String
)
