package com.xsat.androidexpertdicoding.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xsat.androidexpertdicoding.core.databinding.ItemUserBinding
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers

class GithubUserAdapter(private val onItemClick: (GithubUsers) -> Unit) :
    ListAdapter<GithubUsers, GithubUserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onItemClick: (GithubUsers) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUsers) {
            binding.tvUserName.text = user.username
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.ivUserAvatar)

            binding.root.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUsers>() {
            override fun areItemsTheSame(oldItem: GithubUsers, newItem: GithubUsers): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: GithubUsers, newItem: GithubUsers): Boolean {
                return oldItem == newItem
            }
        }
    }
}
