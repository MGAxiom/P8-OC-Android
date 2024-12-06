package com.example.vitesseapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.R
import com.example.vitesseapp.ui.adapters.RecyclerAdapter

class FavorisTabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Example data - replace with your actual data
        val items = listOf("Item 1", "Item 2", "Item 3")
        adapter = RecyclerAdapter(items)
        recyclerView.adapter = adapter
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