package com.ariqhikari.githubuser.ui.detail_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariqhikari.githubuser.data.response.User
import com.ariqhikari.githubuser.databinding.FragmentDetailItemBinding
import com.ariqhikari.githubuser.ui.detail.DetailActivity
import com.ariqhikari.githubuser.ui.detail.DetailViewModel
import com.ariqhikari.githubuser.ui.home.HomeAdapter

class DetailItemFragment: Fragment()  {
    private lateinit var binding : FragmentDetailItemBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailItemBinding.inflate(inflater, container, false)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.layoutManager = layoutManager

        setupData()
    }

    private fun setupData() {
        val username = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_DATA) as String
        val position = arguments?.getInt(ARG_POSITION.toString(), 0)

        if(position == 1) {
            detailViewModel.getFollower(username)

            detailViewModel.listFollower.observe(requireActivity()) { follower ->
                setData(follower)
            }
        } else {
            detailViewModel.getFollowing(username)

            detailViewModel.listFollowing.observe(requireActivity()) { follower ->
                setData(follower)
            }
        }

        detailViewModel.isLoading.observe(requireActivity()) {
            showShimmer(it)
        }
    }

    private fun setData(users: List<User>) {
        val adapter = HomeAdapter()
        adapter.submitList(users)
        binding.rvItems.adapter = adapter
    }

    private fun showShimmer(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerFrameLayout.visibility = View.VISIBLE
        } else {
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = 0
    }
}