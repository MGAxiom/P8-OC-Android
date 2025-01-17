package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.FragmentTabBinding
import com.example.vitesseapp.ui.adapters.RecyclerAdapter
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class TousTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private val viewModel: CandidateViewModel by viewModel()
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCandidates()
//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        // Example data - replace with your actual data
//        val names = listOf("Philippe", "Item 2", "Item 3")
//        val descriptions = listOf("Description 1", "Description 2", "Description 3")
//        adapter = RecyclerAdapter(names, descriptions)
//        recyclerView.adapter = adapter
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = RecyclerAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeCandidates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.candidates.collect { candidates ->
                adapter.updateCandidates(candidates)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetailFragment(candidate: Candidate) {
        val action = HomeFragmentDirections.actionHomescreenFragmentToInfoScreenFragment(1) // TO change with ID
        findNavController().navigate(action)
    }

    companion object {
        private const val ARG_TAB_TITLE = "arg_tab_title"

        fun newInstance(tabTitle: String) = TousTabFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TAB_TITLE, tabTitle)
            }
        }
    }
}