package com.example.recipeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import model.Recipes


class RecipesAdapter(
    private val context: Context,
    private val recipes: ArrayList<Recipes>,
    private val listener: ClickListener
) :
    RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    open interface ClickListener {
        fun onClick(position: Int)

    }

    companion object {
        var clickListener: ClickListener? = null
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.photo)
        val title: TextView = itemView.findViewById(R.id.recipe_title)

        val delete: ImageButton = itemView.findViewById(R.id.delete)
        val edit: ImageButton = itemView.findViewById(R.id.edit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            val intent = Intent(parent.context, RecipeDetails::class.java)
            intent.putExtra("photo", recipes[holder.adapterPosition].photoUri.toString())
            intent.putExtra("title", recipes[holder.adapterPosition].title)
            intent.putExtra("ingredient", recipes[holder.adapterPosition].ingredient)
            intent.putExtra("step", recipes[holder.adapterPosition].step)
            parent.context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        clickListener = listener

        val recipe = recipes[position]
        Picasso.get().load(recipe.photoUri).resize(400, 150).centerCrop().into(holder.image)
        holder.title.text = recipe.title


        holder.delete.setOnClickListener {
            if (clickListener != null)
                clickListener?.onClick(position)

            recipes.removeAt(position)
            notifyDataSetChanged()

        }

        holder.edit.setOnClickListener {
            val intent = Intent(this.context, AddRecipeActivity::class.java)
            intent.putExtra("category", recipes[position].category)
            intent.putExtra("photo", recipes[position].photoUri.toString())
            intent.putExtra("TITLE", recipes[position].title)
            intent.putExtra("INGREDIENT", recipes[position].ingredient)
            intent.putExtra("STEP", recipes[position].step)
            intent.putExtra("position", position)
            (context as Activity).startActivityForResult(intent, 1)
        }


    }
}

