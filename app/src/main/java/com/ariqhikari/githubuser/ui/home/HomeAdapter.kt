package com.ariqhikari.githubuser.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ariqhikari.githubuser.R
import com.ariqhikari.githubuser.data.remote.response.User
import com.ariqhikari.githubuser.databinding.ItemRowUsersBinding
import com.ariqhikari.githubuser.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HomeAdapter : ListAdapter<User, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation")
        fun bind(user: User){
            Glide.with(binding.root)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(250, 250))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(binding.cvAvatar)
            binding.tvUsername.text = user.login
            binding.tvGithub.text = user.htmlUrl

            binding.root.setOnClickListener {
            val intentDetail = Intent(it.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_DATA, user.login)
                it.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}