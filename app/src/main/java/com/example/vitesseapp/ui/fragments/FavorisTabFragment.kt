package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.FragmentTabBinding
import com.example.vitesseapp.ui.adapters.RecyclerAdapter
import kotlinx.coroutines.launch

class FavorisTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavoriteCandidates()
//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        // Example data - replace with your actual data
//        val favouriteCandidates = listOf(Candidate)
//        adapter = RecyclerAdapter(names, descriptions)
//        recyclerView.adapter = adapter
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = RecyclerAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeFavoriteCandidates() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.favoriteCandidates.collect { favorites ->
//                adapter.updateCandidates(favorites)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TAB_TITLE = "arg_tab_title"

        fun newInstance(tabTitle: String) = FavorisTabFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TAB_TITLE, tabTitle)
            }
        }
    }
}