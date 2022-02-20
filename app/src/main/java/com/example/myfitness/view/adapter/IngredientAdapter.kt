package com.example.myfitness.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfitness.databinding.IngredientsListItemsBinding
import com.example.myfitness.model.Ingredients

class IngredientAdapter(private val ingredientList:List<Ingredients>):RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>(){

    class IngredientViewHolder(val binding: IngredientsListItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding= IngredientsListItemsBinding.inflate(inflater,parent,false)

        return IngredientViewHolder(binding)

    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngredient=ingredientList[position]
        holder.binding.IngredientItemTxtView.text=currentIngredient.text
    }

    override fun getItemCount()=ingredientList.size

}

