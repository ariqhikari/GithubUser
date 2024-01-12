package com.ariqhikari.githubuser.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ariqhikari.githubuser.R
import com.ariqhikari.githubuser.data.local.entity.FavoriteEntity
import com.ariqhikari.githubuser.databinding.ItemRowUsersBinding
import com.ariqhikari.githubuser.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteAdapter : ListAdapter<FavoriteEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
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
        fun bind(user: FavoriteEntity){
            Glide.with(binding.root)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(250, 250))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(binding.cvAvatar)
            binding.tvUsername.text = user.username
            binding.tvGithub.text = user.htmlUrl

            binding.root.setOnClickListener {
            val intentDetail = Intent(it.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_DATA, user.username)
                it.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}