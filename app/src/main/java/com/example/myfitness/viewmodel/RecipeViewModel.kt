package com.example.myfitness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitness.model.RecipeBigResponse
import com.example.myfitness.network.RecipeRepository
import com.example.myfitness.utils.UIStates
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository: RecipeRepository):ViewModel(){

    private val _recipeLiveDataResponse=MutableLiveData<RecipeBigResponse>()
            val recipeLiveDataResponse:LiveData<RecipeBigResponse>
            get() = _recipeLiveDataResponse



    private val _uiStates=MutableLiveData<UIStates>()
    val uIStates:LiveData<UIStates>
    get() = _uiStates

    fun getRecipeBigResponse(query:String){

        viewModelScope.launch {
            _uiStates.postValue(UIStates.LOADING)
            val response = recipeRepository.getRecipeBigResponse(query)
            if(response.isSuccessful){
                _recipeLiveDataResponse.postValue(response.body())
                _uiStates.postValue(UIStates.SUCCESS)
            }else
            _uiStates.postValue(UIStates.ERROR)
        }
    }

    fun selectedRecipe(){
        _uiStates.value = UIStates.SELECTED
    }

}
