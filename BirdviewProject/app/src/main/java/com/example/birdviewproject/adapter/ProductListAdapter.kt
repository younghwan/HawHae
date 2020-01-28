package com.example.birdviewproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.birdviewproject.R
import com.example.birdviewproject.model.BaseModel
import com.example.birdviewproject.model.Product
import com.example.birdviewproject.viewholder.ProductViewHolder

class ProductListAdapter(private var itemClickListener: ItemClickListener) : RecyclerView.Adapter<ProductViewHolder>() {
    private val items = mutableListOf<Product>()


    interface ItemClickListener{
        fun onProductClicked(id:Int)
    }

    fun setItems(products: List<Product>) {
        items.run {
            addAll(products)
        }

        notifyDataSetChanged()
    }

    fun clearItems(){
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]

        holder.run{
            bind(items[position])

            itemView.setOnClickListener{
                (product as? Product)?.let { item ->
                    itemClickListener.onProductClicked(product.id)
                }
            }
        }

    }


}

