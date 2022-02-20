package com.example.myfitness.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfitness.R
import com.example.myfitness.databinding.RecipeListItemBinding
import com.example.myfitness.model.Hits
import com.example.myfitness.utils.OnCellClickListener

class RecipeAdapter(private val listener: OnCellClickListener):RecyclerView.Adapter<RecipeViewHolder>() {

    private var recipeList= mutableListOf<Hits>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRecipeList(list:List<Hits>){
        recipeList.clear()
        recipeList.addAll(list)


        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {


       val inflater=LayoutInflater.from(parent.context)
        val binding=RecipeListItemBinding.inflate(inflater,parent,false)

        return RecipeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val currentRecipe=recipeList[position]
        holder.binding.recipeNameTxtView.text=currentRecipe.recipe.label
        val caloriesTxt="Calories ${currentRecipe.recipe.calories.toInt()}"
        holder.binding.CaloriesTxtView.text=caloriesTxt
        val ingredientTxt="Ingredient ${currentRecipe.recipe.ingredients.size}"
        holder.binding.IngredientTxtView.text=ingredientTxt
        Glide.with(holder.binding.recipeImageView)
            .load(currentRecipe.recipe.image)
            .placeholder(R.drawable.nopicture)
            .into(holder.binding.recipeImageView)

        holder.itemView.setOnClickListener {
            listener.onCellClick(currentRecipe)

        }




    }

    override fun getItemCount()=recipeList.size


}

class RecipeViewHolder(val binding: RecipeListItemBinding):RecyclerView.ViewHolder(binding.root)