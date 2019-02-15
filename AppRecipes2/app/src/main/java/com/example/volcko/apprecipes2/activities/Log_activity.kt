package com.example.volcko.apprecipes2.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.R.string.idUser
import com.example.volcko.apprecipes2.data.User
import com.example.volcko.fragmenty.*
import kotlinx.android.synthetic.main.activity_log_activity.*
import kotlinx.android.synthetic.main.app_bar_log_activity.*
import kotlinx.android.synthetic.main.content_log_activity.*

class Log_activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var context: Context
    var manager = supportFragmentManager
    lateinit var dialog : ProgressDialog
    private lateinit var mPrefs: SharedPreferences
    val PREFS_NAME: String = "SL_recipe_data"



    private var idUser: String = "num"
    private var userName: String = "uu"
    private var pass: String = "pp"
    private var email: String = "ee"

    // getter

    fun getIdUser(): String { return idUser }

    fun getUserName(): String { return userName }

    fun getPassword(): String { return pass }

    fun getEmail(): String { return email }

    // setters

    fun setIdUser(str: String) { this.idUser = str }

    fun setUserName(str: String) { this.userName = str }

    fun setPassword(str: String) { this.pass = str }

    fun setEmail(str: String) { this.email = str }


    fun getPreferencesData(): User? {
        val sp: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sp.contains("idUser")) {
            var idUser: String = sp.getString("idUser", "not found")
            var username: String = sp.getString("username", "not found")
            var password: String = sp.getString("pass", "not found")
            var email: String = sp.getString("email", "not found")

            var userData: User =
                User(idUser, username, password, email)

            return userData

        } else {
            return null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_activity)
        setSupportActionBar(toolbar)

        txtToolbarMenu.visibility = View.INVISIBLE

        context = this
        dialog = ProgressDialog(this)

        if(intent.getStringExtra("idUser")==null){
            var arr: Array<User?> = arrayOf(getPreferencesData())
            setIdUser(arr?.get(0)?.getId().toString())
            setUserName(arr?.get(0)?.getUsername().toString())
            setPassword(arr?.get(0)?.getPassword().toString())
            setEmail(arr?.get(0)?.getEmail().toString())
        } else {
            setIdUser(intent.getStringExtra("idUser"))
            setUserName(intent.getStringExtra("username"))
            setPassword(intent.getStringExtra("pass"))
            setEmail(intent.getStringExtra("email"))
        }


        Toast.makeText(this, getIdUser()+" | "+getUserName()+" | "+getPassword()+" | "+getEmail(), Toast.LENGTH_SHORT).show()

        mPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var editor = mPrefs.edit()
        editor.putString("idUser", idUser)
        editor.putString("username", userName)
        editor.putString("pass", pass)
        editor.putString("email", email)
        editor.apply()

        // on log out:
        //mPrefs.edit().clear().apply()


        val btnSearchMain = findViewById<Button>(R.id.btnMainSearch) //btn search in entry page
        val searchBar = findViewById<View>(R.id.search_bar) // view search and filter
        val txtMainSearch = findViewById<EditText>(R.id.txtMainSearch) // edit text Search in entry page
        val txtToolbarSearch = findViewById<EditText>(R.id.txtToolbarSearch) // edit text Search in toolbar
        val txtToolbarMenu = findViewById<TextView>(R.id.txtToolbarMenu) // text view in toolbar for fragment name
        val mainContent = findViewById<View>(R.id.content_main) //view content_main
        val menuLogo = findViewById<ImageView>(R.id.menu_logo) //image view logo in menu


        val btnNavSearch = findViewById<Button>(R.id.btnNavSearch) //btn search in nav bar
        val btnNavFilter = findViewById<Button>(R.id.btnNavFilter) //btn filter in nav bar

        val btnByRecipes = findViewById<Button>(R.id.btnByRecipes) //btn by recipes in home page
        val btnByIngredients = findViewById<Button>(R.id.btnByIngredients) //btn by ingredients in home page

        val recipeName = findViewById<TextView>(R.id.recipe_name) //text view of recipe name

        btnByRecipes.setOnClickListener {
            btnByRecipes.setBackgroundResource(R.drawable.btn_bg)
            btnByIngredients.setBackgroundResource(R.drawable.no_active_btn_bg)
        }

        btnByIngredients.setOnClickListener {
            btnByIngredients.setBackgroundResource(R.drawable.btn_bg)
            btnByRecipes.setBackgroundResource(R.drawable.no_active_btn_bg)
        }

        // set visibility of view
        fun showHideView(view: View) {
            view.visibility = if (view.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        // set visibility of buttons
        fun showHideButton(btn: Button) {
            btn.visibility = if (btn.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        // close keyboard
        fun closeKeyboard(btn: Button) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(btn.getWindowToken(), 0)
        }

        // set visibility of buttons
        fun setNavBarSearch(toolbar: android.support.v7.widget.Toolbar) {
            toolbar.setBackgroundColor(Color.parseColor("#D9000000")) //set background color to dark
            showHideView(searchBar) //set visibility view searchBar
            val txtSearch = txtMainSearch.text
            txtToolbarSearch.text = txtSearch  //set text in toolbar edit text
        }

        // set buttons invisible
        fun setHideNavBarSearch(toolbar: android.support.v7.widget.Toolbar) {
            toolbar.setBackgroundColor(Color.parseColor("#00000000")) //set background color to transparent
            if (searchBar.visibility == View.VISIBLE)
                searchBar.visibility = View.INVISIBLE
            txtToolbarSearch.text = null  //set text in toolbar edit text
        }

        // close keyboard
        fun closeKeyboard() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(btnNavSearch.getWindowToken(), 0)
        }

        showHideView(searchBar) //set visibility view searchBar

        //toggle navigation menu
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)

        // action on button search in entry page
        btnSearchMain.setOnClickListener {
            if (txtMainSearch.text.isEmpty()) {
                Toast.makeText(this, "Have to write something!", Toast.LENGTH_SHORT).show()
            } else {
                var jsonURL = "https://safe-falls-78094.herokuapp.com/meal/meal/"
                var searchTxt = txtMainSearch.text
                var url = jsonURL+searchTxt

                setNavBarSearch(toolbar) //set toolbar
                showHideView(mainContent) //set main content to invisible
                showFragmentSearch() // set fragment search to visible


                //val gv = findViewById<GridView>(R.id.myGridView)
                //JSONDownloader(this, url, gv).execute()
                closeKeyboard(btnSearchMain) // close keyboard
                Toast.makeText(this, txtMainSearch.text, Toast.LENGTH_SHORT).show()
                txtMainSearch.text = null
            }
        }

        btnNavSearch.setOnClickListener {
            showFragmentSearch() // set fragment search to visible
            closeKeyboard(btnNavSearch) // close keyboard

            if (txtToolbarSearch.visibility == View.INVISIBLE){
                txtToolbarMenu.visibility = View.INVISIBLE
                txtToolbarMenu.text = ""
                txtToolbarSearch.visibility = View.VISIBLE
            }

        }

        // action on logo in menu
        menuLogo.setOnClickListener {
            if (mainContent.visibility == View.INVISIBLE)
                mainContent.visibility = View.VISIBLE

            fragment_holder.visibility = View.INVISIBLE
            searchBar.visibility = View.INVISIBLE
            toolbar.setBackgroundColor(Color.parseColor("#00000000")) //set background color to dark
            onBackPressed()
        }


    } //bundle end

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // set visibility of buttons in navbar
    fun setNavBarSearch(toolbar: android.support.v7.widget.Toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#D9000000")) //set background color to dark
        val searchBar = findViewById<View>(R.id.search_bar) // view search and filter
        if (searchBar.visibility == View.INVISIBLE) {
            searchBar.visibility = View.VISIBLE
        }
    }

    //set text to null in toolbar searched edit text
    fun setSearchedTextToNull() {
        txtToolbarSearch.text = null  //set text in toolbar edit text
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val mainContent = findViewById<View>(R.id.content_main) //view content_main
        when (item.itemId) {
            // set action on click item Profile in menu
            R.id.nav_profile -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentProfile() //set fragment profile to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Profile"
            }
            // set action on click item Favorites in menu
            R.id.nav_favorites -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentFavorites() //set fragment favorites to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Favorites"
            }
            // set action on click item Top Rated in menu
            R.id.nav_topRated -> {

                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentTopRated() //set fragment top rated to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Top Rated"

                //val intent = Intent(this, TopRated_activity::class.java)
                // To pass any data to next activity
                //intent.putExtra("keyIdentifier", value)
                // start your next activity
                //startActivity(intent)
            }
            // set action on click item Newest in menu
            R.id.nav_newest -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentNewest() //set fragment newest to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Newest"

            }
            // set action on click item Categories in menu
            R.id.nav_categories -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentCategories() //set fragment categories to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                txtToolbarSearch.visibility = View.INVISIBLE
                //txtToolbarSearch.getLayoutParams().width = 0
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Categories"
            }
            // set action on click item About Us in menu
            R.id.nav_aboutUs -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentAbutUs() //set fragment about us to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "About us"
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //show fragment profile
    fun showFragmentProfile() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentProfile()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment profile
    fun showFragmentFavorites() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentFavorites()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment search
    fun showFragmentSearch() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentSearch()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment top rated
    fun showFragmentTopRated() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentTopRated()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment newest
    fun showFragmentNewest() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentNewest()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment categories
    fun showFragmentCategories() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentCategories()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment about us
    fun showFragmentAbutUs() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentAboutUs()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    // set visibility of view
    fun showHideView(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    // set visibility of buttons
    fun showHideButton(btn: Button) {
        btn.visibility = if (btn.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    fun setSearchNoMatches(){
        val noMAtches = findViewById<TextView>(R.id.no_matches)
        noMAtches.visibility = View.VISIBLE
    }

}