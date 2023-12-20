package com.ariqhikari.githubuser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ariqhikari.githubuser.ui.detail_item.DetailItemFragment

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = DetailItemFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailItemFragment.ARG_POSITION.toString(), position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}