package com.example.mymovielist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovielist.R
import com.example.mymovielist.databinding.ActivityMainBinding
import com.example.mymovielist.model.Movie
import com.example.mymovielist.ui.detail.DetailMovie
import com.example.mymovielist.ui.favorite.FavoriteMovies
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity(), MovieAdapter.FireBaseDataListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var db: FirebaseDatabase
    private lateinit var movieRef: DatabaseReference
    private lateinit var listMovie: ArrayList<Movie>

    private lateinit var mToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.setHasFixedSize(true)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = ""

        mToolbar = binding.toolbar
        setSupportActionBar(mToolbar)

        db = Firebase.database
        movieRef = db.reference.child(MOVIE_CHILD)
        movieRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMovie = ArrayList()
                for (movieSnap in snapshot.children) {
                    movieSnap.getValue(Movie::class.java)?.let { movie ->
                        movie.id = movieSnap.key.toString()
                        listMovie.add(movie)
                    }
                }

                adapter = MovieAdapter(this@MainActivity, listMovie)
                binding.rvMovie.adapter = adapter
            }


            override fun onCancelled(error: DatabaseError) {
                showToast("${error.details} ${error.message}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)

        mToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    val menuItemSearch = menu?.findItem(R.id.search)
                    val searchView: androidx.appcompat.widget.SearchView = menuItemSearch?.actionView as androidx.appcompat.widget.SearchView
                    searchView.queryHint = "Search Movie"
                    searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            searchMovie(newText)
                            return true
                        }
                    })
                    true
                }
                R.id.favorite -> {
                    startActivity(Intent(this, FavoriteMovies::class.java))
                    true
                }
                else -> false
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteMovies::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun searchMovie(keyword: String?) {
        if (keyword != null) {
            val searchedMovie = listMovie.filter {
                it.judul?.contains(keyword, true) == true
            }
            if (searchedMovie.isEmpty()) {
                showToast(getString(R.string.no_movie_found))
            } else {
                adapter.setSearchedList(searchedMovie as ArrayList<Movie>)
            }
        }
    }


    companion object {
        const val MOVIE_CHILD = "Film"
    }

    private fun showToast(text: String?) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    override fun onDataClick(movie: Movie?) {
        val intent = Intent(this, DetailMovie::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }
}