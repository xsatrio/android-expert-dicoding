package com.xsat.androidexpertdicoding.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.xsat.androidexpertdicoding.core.data.Resource
import com.xsat.androidexpertdicoding.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

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

        val username = intent.getStringExtra("username")

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

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

}