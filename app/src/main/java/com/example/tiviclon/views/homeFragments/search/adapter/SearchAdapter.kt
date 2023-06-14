package com.example.tiviclon.views.homeFragments.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiviclon.databinding.ShowSearchViewholderBinding
import com.example.tiviclon.model.application.Show

class SearchAdapter(
    private val shows: List<Show>,
    private val onClick: (show: Show) -> Unit,
    private val onLongClick: (show: Show) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ShowHolder>() {
    class ShowHolder(
        private val binding: ShowSearchViewholderBinding,
        private val onClick: (show: Show) -> Unit,
        private val onLongClick: (show: Show) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int) {
            binding.itemImg.setImageResource(show.image)
            binding.tvTitle.text = show.title
            binding.tvDesc.text = show.description
            binding.clItem.setOnClickListener {
                onClick(show)
            }
            binding.clItem.setOnLongClickListener {
                onLongClick(show)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding =
            ShowSearchViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowHolder(binding, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

    override fun getItemCount() = shows.size
}