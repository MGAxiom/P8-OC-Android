package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.R
import com.example.vitesseapp.databinding.FragmentTabBinding
import com.example.vitesseapp.ui.adapters.RecyclerAdapter

class RecyclerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(dataList: ArrayList<String>): RecyclerFragment {
            val fragment = RecyclerFragment()
            val args = Bundle()
            args.putStringArrayList("data", dataList)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView


        val dataList = arguments?.getStringArrayList("data") ?: arrayListOf()
        val descriptionList = arguments?.getStringArrayList("description") ?: arrayListOf()

        adapter = RecyclerAdapter(dataList, descriptionList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return binding.root
    }
}
