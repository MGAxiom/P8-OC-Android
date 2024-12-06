package com.example.vitesseapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesseapp.R

class RecyclerAdapter(
    private val nameList: List<String>,
    private val descriptionList: List<String>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val candidateName: TextView = itemView.findViewById(R.id.candidate_name)
        val description: TextView = itemView.findViewById(R.id.description)
        val candidateImage: ImageView = itemView.findViewById(R.id.candidate_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.candidates_list_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.candidateName.text = nameList[position]
        holder.description.text = descriptionList[position]
        holder.candidateImage.setImageResource(R.drawable.avatar_gris_placeholder)
    }

    override fun getItemCount(): Int = nameList.size
}