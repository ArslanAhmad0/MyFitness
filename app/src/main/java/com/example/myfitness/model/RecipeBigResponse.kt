package com.example.myfitness.model

data class RecipeBigResponse(
    val hits:List<Hits>
)
data class Hits(
    val recipe:MainRecipe,
    val _links:RecipeLinks
)
data class RecipeLinks(
    val self :ItemRecipeLink,
    val next:NextItemRecipeLink
)
data class ItemRecipeLink(
    val href:String,
    val title:String
)
data class NextItemRecipeLink(
    val href:String,
    val title:String
)

