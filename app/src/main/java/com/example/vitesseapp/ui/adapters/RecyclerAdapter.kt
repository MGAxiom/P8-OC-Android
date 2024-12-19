package com.example.vitesseapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.R
import com.example.vitesseapp.databinding.CandidatesListItemsBinding

class RecyclerAdapter(
    private val nameList: List<String>,
    private val descriptionList: List<String>
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
        holder.bind(nameList[position], descriptionList[position])
    }

    override fun getItemCount(): Int = nameList.size
}
