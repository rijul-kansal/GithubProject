package com.example.githubproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubproject.databinding.ContentBinding
import com.bumptech.glide.Glide
import com.example.githubproject.Model.ShownModel
import com.example.githubproject.R

class Adapter(
    private var items: ArrayList<ShownModel>,
    val context:Context
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text=item.title
        holder.createdDate.text=item.createdDate
        holder.closedDate.text=item.closedDate
        holder.username.text=item.username
        Glide
            .with(context)
            .load(item.userImageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.userImageUrl)
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: ShownModel)
    }
    class ViewHolder(binding: ContentBinding) : RecyclerView.ViewHolder(binding.root) {
        var title = binding.issueTitle
        var createdDate = binding.createdDate
        var closedDate = binding.closedDate
        var username = binding.username
        var userImageUrl = binding.userImage
    }

    fun updateData(newItems: ArrayList<ShownModel>) {
        items = newItems
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}

