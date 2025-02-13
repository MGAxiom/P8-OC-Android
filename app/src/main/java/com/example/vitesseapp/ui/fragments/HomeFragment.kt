package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.vitesseapp.R
import com.example.vitesseapp.databinding.HomeScreenFragmentBinding
import com.example.vitesseapp.ui.adapters.ViewPagerAdapter
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale.filter

class HomeFragment : Fragment() {

    private var _binding: HomeScreenFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CandidateViewModel by activityViewModel()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddCandidate.setOnClickListener {
            val action = HomeFragmentDirections.actionHomescreenFragmentToAddCandidateFragment(
                candidateId = 0,
                isEditMode = false
            )
            findNavController().navigate(action)
        }

        setupViewPager()
        setupSearch()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = R.string.all.toString()
                1 -> tab.text = R.string.favorites.toString()
            }
        }.attach()
    }

    private fun setupSearch() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.setSearchQuery(query)
                updateCurrentTab(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateCurrentTab(query: String) {
        val currentPosition = binding.viewPager.currentItem
        viewPagerAdapter.getFragment(currentPosition).updateSearch(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

