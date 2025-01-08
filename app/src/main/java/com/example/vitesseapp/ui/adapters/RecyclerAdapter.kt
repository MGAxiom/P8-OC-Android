package com.example.vitesseapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.CandidatesListItemsBinding
import com.example.vitesseapp.ui.fragments.HomeFragmentDirections


class RecyclerAdapter(
    private var candidates: List<Candidate>,
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CandidatesListItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, description: String) {
            binding.candidateName.text = name
            binding.description.text = description
            binding.candidateImage.setImageResource(R.drawable.avatar_gris_placeholder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CandidatesListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.bind(candidate.name, candidate.description)
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomescreenFragmentToInfoScreenFragment(position.toLong()) // Replace with my candidate id to retrieve it later
            it.findNavController().navigate(action)
        }
    }

    fun updateCandidates(newCandidates: List<Candidate>) {
        candidates = newCandidates
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = candidates.size
}
