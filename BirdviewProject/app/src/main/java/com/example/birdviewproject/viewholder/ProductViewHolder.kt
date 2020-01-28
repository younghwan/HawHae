package com.example.birdviewproject.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.birdviewproject.R
import com.example.birdviewproject.model.Product
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class ProductViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView!!)
{
    private val mPicasso = Picasso.get()
    private val itemImage = itemView?.findViewById<ImageView>(R.id.item_img_view)
    private val itemDes = itemView?.findViewById<TextView>(R.id.tv_description)
    private val itemPrice = itemView?.findViewById<TextView>(R.id.tv_price)

    private val mNumberFormat = NumberFormat.getNumberInstance(Locale.US)

    fun bind(item : Product) {
        mPicasso.load(item.thumbnailImage).into(itemImage)
        itemDes?.text = item.title
        itemPrice?.text = "${mNumberFormat.format(item.price)}원"
    }
}
