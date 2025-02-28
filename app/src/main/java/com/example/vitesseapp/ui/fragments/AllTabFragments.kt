package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.FragmentTabBinding
import com.example.vitesseapp.ui.adapters.RecyclerAdapter
import com.example.vitesseapp.ui.viewmodels.CandidateViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllTabFragment : Fragment(), TabFragment {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private val viewModel: CandidateViewModel by viewModel()
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCandidates()
        observeLoadingState()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = RecyclerAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeCandidates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.candidates.collect { candidates ->
                    updateUI(candidates)
                }
            }
        }
    }

    private fun observeLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.loadingProgressBar.visibility =
                        if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun updateUI(candidates: List<Candidate>) {
        if (candidates.isNotEmpty()) {
            binding.noDataTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            binding.noDataTextView.visibility = View.VISIBLE
        }
        adapter.updateCandidates(candidates)
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadCandidates("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun updateSearch(query: String) {
        if (isAdded) {
            viewModel.loadCandidates(query)
        }
    }
}