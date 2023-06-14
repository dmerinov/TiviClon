package com.example.tiviclon.views.homeFragments.library.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiviclon.databinding.LibraryShowViewholderBinding
import com.example.tiviclon.model.application.Show

class LibraryAdapter(
    private val shows: List<Show>,
    private val onClick: (show: Show) -> Unit,
    private val onLongClick: (title: String) -> Unit,
    private val context: Context?
) :
    RecyclerView.Adapter<LibraryAdapter.ShowHolder>() {


    class ShowHolder(
        private val binding: LibraryShowViewholderBinding,
        private val onClick: (show: Show) -> Unit,
        private val onLongClick: (title: String) -> Unit,
        private val context: Context?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int) {
            with(binding) {
                tvTitle.text = show.title
                context?.let {
                    Glide.with(it).load(show.image).into(itemImg)
                }
                tvDesc.text = show.status
                clItem.setOnClickListener {
                    onClick(show)
                }
                clItem.setOnLongClickListener {
                    onLongClick(show.title)
                    true
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding =
            LibraryShowViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowHolder(binding, onClick, onLongClick, context)
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

}