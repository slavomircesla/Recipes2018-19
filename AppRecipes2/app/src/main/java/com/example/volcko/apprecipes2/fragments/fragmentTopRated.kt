package com.example.volcko.fragmenty

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.adapter.AllMealAdapter
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.testhttpcon.AsyncMealGetAll
import java.util.*

class fragmentTopRated: Fragment(){
    val TAG = "FragmentTopRated"
    private var fav: Boolean = true
    private var recipes = ArrayList<Recipes>()
    private lateinit var adapter: AllMealAdapter
    /*
    private val adapter = AllMealAdapter(Collections.emptyList()


    fun setFav(x: Boolean) {
        this.fav = x
    }

    fun getFav(): Boolean {
        return fav
    }
    */
    override fun onAttach(context: Context?) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")

        val view: View = inflater!!.inflate(R.layout.fragment_top_rated, container, false)

        val btnFav = view.findViewById<Button>(R.id.btnFav) // btn favorite in recipe view

        val recipeView = view.findViewById<View>(R.id.recipeView)


        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewTopRated)

        rv.layoutManager = LinearLayoutManager(context)

        adapter = AllMealAdapter()


        rv.adapter = adapter

        AsyncMealGetAll(context, true, adapter).execute()

        //AsyncMealGetAll(context, rv, getFav()).execute()
        //AsyncMealGetAll(context, rv, true, AllMealAdapter(recipes)).execute()



        /*
        if (getFav()==false)
            btnFav.visibility = View.INVISIBLE
        else {
            btnFav.setOnClickListener {
                if (btnFav.tag.equals("noFav")){
                    btnFav.setBackgroundResource(R.drawable.ic_fav)
                    btnFav.tag = "fav"
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                } else {
                    btnFav.setBackgroundResource(R.drawable.ic_fav_empty)
                    btnFav.tag = "noFav"
                    Toast.makeText(context, "Remove from Favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
        */


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG,"onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG,"onResume")
        super.onResume()
    }

    override fun onStop() {
        Log.d(TAG,"onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG,"onDetach")
        super.onDetach()
    }

    
}
