package com.example.volcko.apprecipes2.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.mapJson.Category
import com.example.volcko.apprecipes2.mapJson.MealCategory
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.fragmenty.fragmentByCategory
import com.example.volcko.fragmenty.fragmentSearch
import kotlinx.android.synthetic.main.category_view_red.view.*

class CategoryAdapter(val c: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var loadActivity: String
    private var categories = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_view_black, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = categories[position]?.category
        holder.categoryName.text = cat
        val layout = holder.itemView.findViewById<LinearLayout>(R.id.backLayoutCategory)
        if(position %2 == 1) {
            layout.setBackgroundResource(R.drawable.category_view_black_bg)
        } else {
            layout.setBackgroundResource(R.drawable.category_view_red_bg)
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(c, holder.categoryName.text.toString(), Toast.LENGTH_SHORT).show()
            val bundle: Bundle = Bundle()
            bundle.putString("category", holder.categoryName.text.toString())
            bundle.putString("act", loadActivity)
            var transaction: FragmentTransaction

            if (loadActivity.equals("noLog"))
                transaction = (c as NoLog_activity).supportFragmentManager.beginTransaction()
            else
                transaction = (c as Log_activity).supportFragmentManager.beginTransaction()
            val fragment = fragmentByCategory()

            fragment.arguments = bundle
            transaction.replace(R.id.fragment_holder, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val categoryName: TextView = itemView.categoryName
    }

    fun setData(xy: String, cat: ArrayList<Category>) {
        loadActivity = xy
        categories = cat
        notifyDataSetChanged()
    }
}
