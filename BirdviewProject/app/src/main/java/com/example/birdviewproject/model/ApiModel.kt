package com.example.birdviewproject.model

import android.widget.ImageView
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*


data class ProductsResp(
    @SerializedName("body")
    var body: List<Product> = emptyList(),
    @SerializedName("statusCode")
    var statusCode: Int = 0
)

data class Product(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("thumbnail_image")
    var thumbnailImage: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("price")
    var price: Int = 0,
    @SerializedName("oily_score")
    var oilyScore: Int = 0,
    @SerializedName("dry_score")
    var dryScore: Int = 0,
    @SerializedName("sensitive_score")
    var sensitiveScore: Int = 0
)

data class ProductDetailResp(
    @SerializedName("body")
    var body: ProductDetail,
    @SerializedName("statusCode")
    var statuscode: Int = 0
)

data class ProductDetail(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("full_size_image")
    var fullImage: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var desc: String = "",
    @SerializedName("price")
    var price: Int = 0,
    @SerializedName("oily_score")
    var oilyScore: Int = 0,
    @SerializedName("dry_score")
    var dryScore: Int = 0,
    @SerializedName("sensitive_score")
    var sensitiveScore: Int = 0
) {
    val priceText: String
        get() = "${NumberFormat.getNumberInstance(Locale.US).format(price)}원"
}