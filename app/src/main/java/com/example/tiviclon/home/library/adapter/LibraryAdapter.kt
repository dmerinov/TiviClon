package com.example.tiviclon.home.library.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiviclon.databinding.ShowViewholderBinding
import com.example.tiviclon.model.application.Show

class LibraryAdapter(private val shows: List<Show>) :
    RecyclerView.Adapter<LibraryAdapter.ShowHolder>() {


    class ShowHolder(private val binding: ShowViewholderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int){
            binding.itemTitle.text = show.title
            if(totalItems - 1 == adapterPosition){
                binding.spacer.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding = ShowViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShowHolder(binding)
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

}