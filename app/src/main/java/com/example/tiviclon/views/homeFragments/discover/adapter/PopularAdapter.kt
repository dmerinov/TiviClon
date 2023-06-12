package com.example.tiviclon.views.homeFragments.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiviclon.databinding.ShowViewholderBinding
import com.example.tiviclon.model.application.Show

class PopularAdapter(private val shows: List<Show>, private val onClick: (show: Show) -> Unit) :
    RecyclerView.Adapter<PopularAdapter.ShowHolder>() {


    class ShowHolder(private val binding: ShowViewholderBinding, private val onClick: (show: Show) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show, totalItems: Int){
            with(binding){
                itemImg.setImageResource(show.image)
                cvItem.setOnClickListener {
                    onClick(show)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val binding = ShowViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShowHolder(binding, onClick)
    }

    override fun getItemCount() = shows.size

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        holder.bind(shows[position], shows.size)
    }

}