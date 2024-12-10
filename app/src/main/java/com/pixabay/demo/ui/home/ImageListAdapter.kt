package com.pixabay.demo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pixabay.demo.databinding.ItemImageBinding
import com.pixabay.demo.domain.model.Photo

class ImageListAdapter(
    private val clickListener: OnItemClickListener
) : ListAdapter<Photo, ImageListAdapter.ImageViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(image: Photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Photo, listener: OnItemClickListener) {
            binding.image = image
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}
