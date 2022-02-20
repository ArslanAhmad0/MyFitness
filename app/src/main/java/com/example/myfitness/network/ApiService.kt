package com.example.myfitness.network

import com.example.myfitness.model.RecipeBigResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //main response https://api.edamam.com/api/recipes/v2?type=public&q=Chicken+Paprikash&app_id=2474b9d3&app_key=5151c102290960eb95b27d3ec60c6d37
    // https://api.edamam.com/api/recipes/v2?type=public&q=chicken&app_id=2474b9d3&app_key=5151c102290960eb95b27d3ec60c6d37&field=label
    @GET("v2")
   suspend fun getRecipeBigResponse(@Query("type") type:String="public",
                                    @Query("q")query:String,
                                    @Query("random") random:Boolean,
                                    @Query("app_id") app_id:String="2474b9d3",
                                    @Query("app_key")appKey:String="5151c102290960eb95b27d3ec60c6d37"
                            ):Response<RecipeBigResponse>

    @GET("v2")
    suspend fun getRecipeBigResponse(@Query("type") type:String="public",
                                     @Query("q")query:String,
                                     @Query("app_id") app_id:String="2474b9d3",
                                     @Query("app_key")appKey:String="5151c102290960eb95b27d3ec60c6d37"
    ):Response<RecipeBigResponse>






    companion object{
       private var retrofit:Retrofit?=null

        fun getRetrofit():ApiService{
            if(retrofit==null){
                retrofit=Retrofit.Builder()
                    .baseUrl("https://api.edamam.com/api/recipes/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiService::class.java)
        }
    }
}