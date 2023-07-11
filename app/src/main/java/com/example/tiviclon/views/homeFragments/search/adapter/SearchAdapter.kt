package com.example.tiviclon.views.homeFragments.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiviclon.databinding.ShowSearchViewholderBinding
import com.example.tiviclon.model.application.Show

class SearchAdapter(
    private val shows: MutableList<Show>,
    private val onClick: (show: Show) -> Unit,
    private val onLongClick: (show: Show) -> Unit,
    private val context: Context?
) :
    RecyclerView.Adapter<SearchAdapter.ShowHolder>() {
    class ShowHolder(
        private val binding: ShowSearchViewholderBinding,
        private val onClick: (show: Show) -> Unit,
        private val onLongClick: (show: Show) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int) {
            with(binding) {
                context?.let {
                    Glide.with(context).load(show.image).into(itemImg)
                }
                tvTitle.text = show.title
                tvDesc.text = show.status
                clItem.setOnClickListener {
                    onClick(show)
                }
                clItem.setOnLongClickListener {
                    onLongClick(show)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding =
            ShowSearchViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowHolder(binding, onClick, onLongClick, context)
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

    override fun getItemCount() = shows.size

    fun swapList(list: List<Show>) {
        shows.clear()
        shows.addAll(list)
        notifyDataSetChanged()
    }
}