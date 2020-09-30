package com.example.dogs.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogs.R
import com.example.dogs.extensions.inflate
import kotlinx.android.synthetic.main.dog_result_view.view.*

class DogResultAdapter(private val dogResults: List<String>): RecyclerView.Adapter<DogResultAdapter.DogResultViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogResultViewHolder {
        val view = parent.inflate(R.layout.dog_result_view, false)
        return DogResultViewHolder(view)
    }


    override fun getItemCount(): Int = dogResults.size

    override fun onBindViewHolder(holder: DogResultViewHolder, position: Int) {
        Glide.with(holder.dogImageView.context).load(dogResults[position]).into(holder.dogImageView)
    }


    class DogResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dogImageView: ImageView = itemView.dog_image_view
    }
}