package com.xsat.androidexpertdicoding.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsat.androidexpertdicoding.R
import com.xsat.androidexpertdicoding.core.data.Resource
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.core.ui.GithubUserAdapter
import com.xsat.androidexpertdicoding.databinding.ActivityMainBinding
import com.xsat.androidexpertdicoding.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GithubUserAdapter
    private val viewModel: MainActivityViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences
    private var isDarkMode: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        isDarkMode = sharedPreferences.getBoolean("theme", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTopAppBar()
        setupRecyclerView()
        observeUsers()
    }

    private fun setupTopAppBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val username = "xsatrio"
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, Class.forName("com.xsat.androidexpertdicoding.favorite.FavoriteActivity"))
                    startActivity(intent)
                    true
                }
                R.id.menu3 -> {
                    // Toggle dark mode
                    if (isDarkMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sharedPreferences.edit().putBoolean("theme", false).apply()
                        isDarkMode = false
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sharedPreferences.edit().putBoolean("theme", true).apply()
                        isDarkMode = true
                    }
                    true
                }
                else -> false
            }
        }
    }


    private fun setupRecyclerView() {
        adapter = GithubUserAdapter { user ->
            openDetailActivity(user)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)
    }

    private fun observeUsers() {
        viewModel.user.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    result.data?.let { adapter.submitList(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openDetailActivity(user: GithubUsers) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("username", user.username)
            putExtra("user_data", user)
            putExtra("isFavorite", true)
        }
        startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
