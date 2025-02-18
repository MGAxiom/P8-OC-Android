package com.example.vitesseapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vitesseapp.R
import com.example.vitesseapp.data.models.Candidate
import com.example.vitesseapp.databinding.CandidatesListItemsBinding
import com.example.vitesseapp.ui.fragments.HomeFragmentDirections


class RecyclerAdapter(
    private var candidates: List<Candidate>,
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: CandidatesListItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, notes: String, image: String) {
            binding.candidateName.text = name
            binding.description.text = notes
            if (image.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(image)
                    .placeholder(R.drawable.avatar_gris_placeholder)
                    .error(R.drawable.avatar_gris_placeholder)
                    .into(binding.candidateImage)
            } else {
                binding.candidateImage.setImageResource(R.drawable.avatar_gris_placeholder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CandidatesListItemsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        val candidate = candidates[position]
        holder.bind(candidate.name, candidate.notes, candidate.imageResUri)
        holder.itemView.setOnClickListener {
            val action = candidate.id?.let { candidate ->
                HomeFragmentDirections.actionHomescreenFragmentToInfoScreenFragment(
                    candidate
                )
            }
            if (action != null) {
                it.findNavController().navigate(action)
            }
        }
    }

    fun updateCandidates(newCandidates: List<Candidate>) {
        candidates = newCandidates
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = candidates.size
}
