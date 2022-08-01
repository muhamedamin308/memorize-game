package com.rkpandey.mymemory

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rkpandey.mymemory.models.BoardSize
import kotlin.math.min

class ImagePickerAdapter(
    private val context: Context,
    private val ImagesUris: List<Uri>,
    private val boardSize: BoardSize,
    private val imageClickListener: ImageClickListener)
    : RecyclerView.Adapter<ImagePickerAdapter.ViewHolder>() {

    interface ImageClickListener {
        fun onPlaceHolderClick()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_image, parent, false)
        val cardWidth = parent.width / boardSize.getWidth()
        val cardHeight = parent.height / boardSize.getHeight()
        val cardSideLine = min(cardWidth , cardHeight)
        val layoutParams = view.findViewById<ImageView>(R.id.ivCustomImage).layoutParams
        layoutParams.width = cardSideLine
        layoutParams.height = cardSideLine
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < ImagesUris.size){
            holder.bind(ImagesUris[position])
        }else {
            holder.bind()
        }
    }

    override fun getItemCount() = boardSize.getNumPairs() 
    
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {
        private val ivCustomImage = itemView.findViewById<ImageView>(R.id.ivCustomImage)
        fun bind(){
            ivCustomImage.setOnClickListener{
                imageClickListener.onPlaceHolderClick()
            }
        }
        fun bind(uri : Uri){
            ivCustomImage.setImageURI(uri)
            ivCustomImage.setOnClickListener(null)
        }
    }

}
