package com.example.tiviclon.views.detailShow.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiviclon.databinding.ShowViewholderBinding
import com.example.tiviclon.model.application.Show

class DetailAdapter(private val shows: List<Show>, private val onClick: (show: Show) -> Unit, private val context: Context?) :
    RecyclerView.Adapter<DetailAdapter.ShowHolder>() {


    class ShowHolder(
        private val binding: ShowViewholderBinding,
        private val onClick: (show: Show) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int) {
            with(binding) {
                context?.let {
                    Glide.with(it).load(show.image).into(itemImg)
                }
                cvItem.setOnClickListener {
                    onClick(show)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding =
            ShowViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowHolder(binding, onClick, context)
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

}