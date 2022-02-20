package com.example.myfitness.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfitness.R
import com.example.myfitness.databinding.ActivityMainBinding
import com.example.myfitness.model.Hits
import com.example.myfitness.network.ApiService
import com.example.myfitness.network.RecipeRepository
import com.example.myfitness.utils.OnCellClickListener
import com.example.myfitness.utils.UIStates
import com.example.myfitness.view.adapter.RecipeAdapter
import com.example.myfitness.viewmodel.RecipeViewModel
import com.example.myfitness.viewmodel.RecipeViewModelFactory

class MainActivity : AppCompatActivity(),OnCellClickListener{

    private lateinit var binding: ActivityMainBinding
    private val recipeAdapter= RecipeAdapter(this)
    private val service=ApiService.getRetrofit()
    private val repository=RecipeRepository(service)
    private var recipeName:String=""
    private val viewModel:RecipeViewModel by lazy {
        ViewModelProvider(this,
        RecipeViewModelFactory(repository))[RecipeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recipesRview.adapter=recipeAdapter
        binding.recipesRview.layoutManager=GridLayoutManager(this,2)
        binding.searchBTN.setOnClickListener {

            recipeName = binding.recipeNameET.text.toString()
            if(recipeName.isEmpty()){
                Toast.makeText(this,"Please Enter Recipe Name",Toast.LENGTH_SHORT).show()
            }else

                viewModel.getRecipeBigResponse(query=recipeName)
                }
        configureObserver()

    }

    private fun configureObserver() {

        viewModel.recipeLiveDataResponse.observe(this)
            {
            recipeAdapter.setRecipeList(it.hits)

            }
        viewModel.uIStates.observe(this){
            when(it!!){
                UIStates.SUCCESS->{
                    binding.searchBTN.isClickable=true
                    binding.searchBTN.setBackgroundColor(Color.BLUE)
                    binding.mainview.visibility=View.VISIBLE

                }
                UIStates.ERROR->{
                    binding.recipesRview.visibility=View.GONE
                    Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show()
                }
                UIStates.LOADING->{
                    binding.searchBTN.isClickable=false
                    binding.searchBTN.setBackgroundColor(Color.GRAY)

                }
                UIStates.SELECTED->{
                    binding.mainview.visibility=View.INVISIBLE

                }

            }
        }
    }

    override fun onCellClick(recipe: Hits) {
        viewModel.selectedRecipe()
        val fragment=RecipeFragment(recipe,viewModel)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragCont_view,fragment)
            .addToBackStack("")
            .setReorderingAllowed(true)
            .commit()

        val data=Bundle()
        data.putString("searchedItem",recipeName)
        fragment.arguments=data

    }
}