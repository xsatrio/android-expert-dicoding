package com.xsat.androidexpertdicoding.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.xsat.androidexpertdicoding.core.data.Resource
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    private var githubUser: GithubUsers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        githubUser = intent.getParcelableExtra("user_data")

        githubUser?.let {
            viewModel.setInitialFavoriteState(it.isFavorite)
        }

        val username = intent.getStringExtra("username")
        val isFavoriteView = intent.getBooleanExtra("isFavorite", false)

        if (username != null) {
            viewModel.getDetailUser(username).observe(this) { result ->
                when (result) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val user = result.data
                        binding.tvName.text = user?.name ?: "No Name"
                        binding.tvUsername.text = user?.login
                        binding.tvId.text = "ID: ${user?.id}"
                        binding.tvFollowersCount.text = "Followers: ${user?.followers}"
                        binding.tvFollowingCount.text = "Following: ${user?.following}"
                        Glide.with(this)
                            .load(user?.avatarUrl)
                            .into(binding.ivUserAvatar)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        setUpFloatingActionButton(isFavoriteView)

        viewModel.favoriteState.observe(this) { isFav ->
            updateFavoriteState(isFav)
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpFloatingActionButton(isFavoriteView: Boolean?) {
        if (isFavoriteView == false) {
            binding.fabFavorite.visibility = View.GONE
        } else {
            binding.fabFavorite.visibility = View.VISIBLE
            binding.fabFavorite.setOnClickListener {
                githubUser?.let {
                    lifecycleScope.launch {
                        viewModel.toggleFavorite(it)
                    }
                }
            }
        }
    }


    private fun updateFavoriteState(isFavorite: Boolean) {
        binding.fabFavorite.setImageResource(
            if (isFavorite) com.xsat.androidexpertdicoding.R.drawable.baseline_favorite_24
            else com.xsat.androidexpertdicoding.R.drawable.baseline_favorite_border_24
        )
    }
}
