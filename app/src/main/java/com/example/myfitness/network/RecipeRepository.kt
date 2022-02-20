package com.example.myfitness.network

import java.util.*

class RecipeRepository(private val service: ApiService) {

    suspend fun getRecipeBigResponse(query:String,random:Boolean) = service.getRecipeBigResponse(query = query, random = random)
    suspend fun getRecipeBigResponse(query: String)=service.getRecipeBigResponse(query=query)

}