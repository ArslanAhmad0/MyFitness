package com.example.myfitness.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myfitness.R
import com.example.myfitness.databinding.FragmentRecipeBinding
import com.example.myfitness.model.Hits
import com.example.myfitness.view.adapter.IngredientAdapter
import com.example.myfitness.viewmodel.RecipeViewModel

class RecipeFragment(private val recipeItem: Hits, private val viewModel: RecipeViewModel):Fragment() {

   private lateinit var binding:FragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRecipeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val calories="Calories :${recipeItem.recipe.calories.toInt()}"
        binding.RecipeLinkBtn.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW)
            val url=recipeItem.recipe.url
                intent.setData(Uri.parse(url))
            startActivity(intent)
        }

        binding.recipeNameTxtView.text=recipeItem.recipe.label

        binding.CaloriesServTxtView.text=calories
        binding.PerServingET.addTextChangedListener {
            val persons = if(binding.PerServingET.text.isEmpty()) {
                1
            } else

                binding.PerServingET.text.toString().toInt()
            val perPersonCal="Calories :${(recipeItem.recipe.calories / persons).toInt()}"
            binding.CaloriesServTxtView.text =perPersonCal

        }

        binding.IngredientsRV.adapter= IngredientAdapter(recipeItem.recipe.ingredients)
        binding.IngredientsRV.layoutManager=LinearLayoutManager(context)

        Glide.with(binding.imageView)
            .load(recipeItem.recipe.image)
            .placeholder(R.drawable.nopicture)
            .into(binding.imageView)


    }

    override fun onDestroy() {
        super.postponeEnterTransition()
        super.onDestroy()
        val searchedItem:String
        val bundle:Bundle = requireArguments()
        searchedItem=   bundle.getString("searchedItem").toString()
        viewModel.getRecipeBigResponse(searchedItem)

    }


}