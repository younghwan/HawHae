package com.example.birdviewproject.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.birdviewproject.R
import com.example.birdviewproject.common.Const
import com.example.birdviewproject.model.ProductDetail
import com.example.birdviewproject.model.ProductDetailResp
import com.example.birdviewproject.network.ApiService
import com.example.birdviewproject.network.ApiService.Companion.service
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import retrofit2.awaitResponse
import java.lang.invoke.ConstantCallSite

class ProductDetailActivity : AppCompatActivity() {


    private lateinit var mProductImage: ImageView
    private lateinit var mProductName: TextView
    private lateinit var mProductPrice: TextView
    private lateinit var mProductDesc: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val id = intent?.getIntExtra(Const.EXTRA_ID,-1) ?: -1

        mProductImage = findViewById(R.id.detail_img)
        mProductDesc = findViewById(R.id.detail_desc)
        mProductPrice = findViewById(R.id.detail_price)
        mProductName = findViewById(R.id.detail_name)

        initViews()
        loadDetailViews(id)

    }

    private fun initViews() {
        supportActionBar?.run{
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

    }


    @SuppressLint("CheckResult")
    private fun loadDetailViews(id : Int){
        service.getProductDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                update(it.body)
            }
                ,{
                    Toast.makeText(this,"상품 정보 조회에 실패하였습니다.",Toast.LENGTH_SHORT).show()
                }

            )
    }

    private fun update(productDetail: ProductDetail) {
        Picasso.get().load(productDetail.fullImage).into(mProductImage)

        mProductName.text = productDetail.title
        mProductPrice.text = productDetail.priceText
        mProductDesc.text = productDetail.desc
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_stay_anim, R.anim.activity_exit_anim)
    }

}
