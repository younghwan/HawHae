package com.example.birdviewproject.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.birdviewproject.model.BaseModel


abstract class BaseViewHolder<T : BaseModel>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun update(item: T)
}