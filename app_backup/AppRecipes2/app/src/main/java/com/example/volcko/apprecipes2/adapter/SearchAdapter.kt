package com.example.volcko.apprecipes2.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.fragmenty.fragmentByCategory
import com.example.volcko.fragmenty.fragmentRecipeDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_view.view.*

class SearchAdapter(
    //val recipes: List<Recipes>
    val c: Context?
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var recipes = ArrayList<Recipes>()
    private lateinit var noMatches: TextView

    private lateinit var loadActivity: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (recipes.isEmpty())
            noMatches.visibility = View.VISIBLE
        else {
            noMatches.visibility = View.INVISIBLE

            val recipe = recipes[position]
            holder.itemView.recipe_name.text = recipe.meal
            holder.itemView.recipe_num_review.text = recipe.review.toString()
            holder.itemView.recipe_label.text = recipe.category

            val width = 360
            val height = 175
            val btnFav = holder.itemView.btnFav

            val recipe_img = holder.itemView.recipe_img
            Picasso.with(holder.itemView.context)
                .load(recipe.image)
                .resize(width, height)
                .onlyScaleDown()
                .into(recipe_img)

            holder.itemView.setOnClickListener {
                Toast.makeText(c, holder.recipeName.text.toString(), Toast.LENGTH_SHORT).show()
                val bundle: Bundle = Bundle()
                bundle.putString("id", recipe.id)
                bundle.putString("act", loadActivity)
                var transaction: FragmentTransaction

                if (loadActivity.equals("noLog"))
                    transaction = (c as NoLog_activity).supportFragmentManager.beginTransaction()
                else
                    transaction = (c as Log_activity).supportFragmentManager.beginTransaction()
                val fragment = fragmentRecipeDetail()

                fragment.arguments = bundle
                transaction.replace(R.id.fragment_holder, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            holder.itemView.recipe_name.setOnClickListener {
                Toast.makeText(c, holder.recipeName.text.toString(), Toast.LENGTH_SHORT).show()
                val bundle: Bundle = Bundle()
                bundle.putString("id", recipe.id)
                bundle.putString("act", loadActivity)
                var transaction: FragmentTransaction

                if (loadActivity.equals("noLog"))
                    transaction = (c as NoLog_activity).supportFragmentManager.beginTransaction()
                else
                    transaction = (c as Log_activity).supportFragmentManager.beginTransaction()
                val fragment = fragmentRecipeDetail()

                fragment.arguments = bundle
                transaction.replace(R.id.fragment_holder, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }


            btnFav.setOnClickListener {
                if (btnFav.tag.equals("noFav")){
                    btnFav.setBackgroundResource(R.drawable.ic_fav)
                    btnFav.tag = "fav"
                    Toast.makeText(c, "Added to Favorites", Toast.LENGTH_SHORT).show()
                } else {
                    btnFav.setBackgroundResource(R.drawable.ic_fav_empty)
                    btnFav.tag = "noFav"
                    Toast.makeText(c, "Remove from Favorites", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recipeName: TextView = itemView.recipe_name
    }

    fun setData(
        xy: ArrayList<Recipes>,
        matches: TextView,
        act: String
    ) {
        recipes = xy
        noMatches = matches
        loadActivity = act
        notifyDataSetChanged()
    }

}
