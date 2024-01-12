package com.ariqhikari.githubuser.ui.detail_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariqhikari.githubuser.data.Result
import com.ariqhikari.githubuser.data.remote.response.User
import com.ariqhikari.githubuser.databinding.FragmentDetailItemBinding
import com.ariqhikari.githubuser.ui.ViewModelFactory
import com.ariqhikari.githubuser.ui.detail.DetailActivity
import com.ariqhikari.githubuser.ui.detail.DetailViewModel
import com.ariqhikari.githubuser.ui.home.HomeAdapter

class DetailItemFragment: Fragment()  {
    private lateinit var binding : FragmentDetailItemBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        val username = requireActivity().intent.getStringExtra(DetailActivity.EXTRA_DATA) as String
        val position = arguments?.getInt(ARG_POSITION.toString(), 0)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.layoutManager = layoutManager

        val adapter = HomeAdapter()
        binding.rvItems.adapter = adapter

        if(position == 1) {
            detailViewModel.getFollowers(username).observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                        showShimmer(true)
                        }
                        is Result.Success -> {
                            showShimmer(false)
                            adapter.submitList(result.data)
                        }
                        is Result.Error -> {
                            showShimmer(false)
                            Toast.makeText(
                                requireContext(),
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else {
            detailViewModel.getFollowings(username).observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showShimmer(true)
                        }
                        is Result.Success -> {
                            showShimmer(false)
                            adapter.submitList(result.data)
                        }
                        is Result.Error -> {
                            showShimmer(false)
                            Toast.makeText(
                                requireContext(),
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
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