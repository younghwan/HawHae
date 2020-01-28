package com.example.birdviewproject.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.birdviewproject.R
import com.example.birdviewproject.viewholder.BaseViewHolder
import com.example.birdviewproject.viewholder.DummyViewHolder
import com.example.birdviewproject.viewholder.ProductViewHolder

enum class ViewType(
    var viewType: Int,
    var resID: Int,
    var clazz: Class<*>
) {
    DUMMY(0, R.layout.item_dummy, DummyViewHolder::class.java),
    PRODUCT(1, R.layout.activity_product_detail, ProductViewHolder::class.java);


    companion object {
        private fun get(viewType: Int): ViewType {
            return values().find { it.viewType == viewType } ?: PRODUCT
        }

        fun getViewHolder(parent : ViewGroup, viewType: Int) : BaseViewHolder<BaseModel> {
            val itemViewType = get(viewType)
            val view = LayoutInflater.from(parent.context).inflate(itemViewType.resID,parent,false)
            val dummyViewHolder = DummyViewHolder(view)


            return itemViewType.clazz.getConstructor(View::class.java).newInstance(view) as BaseViewHolder<BaseModel>

        }

    }
}