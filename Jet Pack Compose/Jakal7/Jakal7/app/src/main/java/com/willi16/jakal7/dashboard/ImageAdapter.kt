package com.willi16.jakal7.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willi16.jakal7.R
import com.willi16.jakal7.dashboard.model.ImageData

class ImageAdapter(private var imageList: ArrayList<ImageData>):RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ImageHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.item_image_adapter, parent, false)
        return ImageHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ImageHolder, position: Int) {
        holder.bindItem(imageList[position],context)
    }

    override fun getItemCount(): Int = imageList.size

    class ImageHolder(view: View):RecyclerView.ViewHolder(view) {
        private var poster : ImageView = view.findViewById(R.id.iv_slider)

        fun bindItem(imageList: ImageData, context: Context) {
            Glide.with(context)
                .load(imageList.imageUrl)
                .into(poster)
        }

    }
}