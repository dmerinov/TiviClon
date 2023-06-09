package com.example.tiviclon.views.homeFragments.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiviclon.databinding.LibraryShowViewholderBinding
import com.example.tiviclon.model.application.Show

class LibraryAdapter (private val shows: List<Show>, private val onClick: (show:Show) -> Unit) :
    RecyclerView.Adapter<LibraryAdapter.ShowHolder>() {


    class ShowHolder(private val binding: LibraryShowViewholderBinding, private val onClick: (show:Show) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int){
            binding.tvTitle.text = show.title
            binding.itemImg.setImageResource(show.image)
            binding.clItem.setOnClickListener {
                onClick(show)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding = LibraryShowViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShowHolder(binding, onClick)
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

}