package com.example.birdviewproject.viewmdoel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.birdviewproject.model.Product
import com.example.birdviewproject.model.SkinType
import com.example.birdviewproject.network.ApiService
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    val products = MutableLiveData<List<Product>>()
    val currentPage = MutableLiveData<Int>()

    private var mLoadingRequest: Map<String, String>? = null

    fun loadProducts(skinType: SkinType, page: Int = 1, keword: String = "") {
        val paramMap = mutableMapOf(
            "page" to page.toString()
        ).apply {
            if (skinType != SkinType.ALL) {
                put("skin_type", skinType.value)
            }

            if (keword.isNotBlank()) {
                put("search", keword)
            }
        }

        if(mLoadingRequest == paramMap){
            return
        }

        ApiService.service.getProuducts(paramMap).map {
            val productList = it.body

            productList.sortedByDescending { product ->
                when (skinType) {
                    SkinType.DRY -> product.dryScore
                    SkinType.SENSITIVE -> product.sensitiveScore
                    else -> product.oilyScore
                }

            }
            return@map productList
        }.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({
            products.postValue(it)
        }, { products.postValue(emptyList()) })
    }
}
