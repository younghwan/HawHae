package com.example.birdviewproject.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birdviewproject.R
import com.example.birdviewproject.network.ApiService.Companion.service
import com.example.birdviewproject.adapter.ProductListAdapter
import com.example.birdviewproject.common.Const.Companion.EXTRA_ID
import com.example.birdviewproject.model.Product
import com.example.birdviewproject.model.SkinType
import com.example.birdviewproject.viewmdoel.ProductListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: ProductListAdapter
    private lateinit var mViewModel: ProductListViewModel
    private lateinit var mSkinFilter: TextView
    private lateinit var mSearchKeyWord: AppCompatEditText

    private var mCurrentPage: Int = 1

    private var mSelectedSkinType: SkinType = SkinType.ALL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initViewModel()

        loadProductList()
    }

    private fun initViews() {
        mAdapter = ProductListAdapter(object : ProductListAdapter.ItemClickListener {
            override fun onProductClicked(id: Int) {
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                intent.putExtra(EXTRA_ID, id)
                startActivity(intent)
                overridePendingTransition(R.anim.activity_enter_anim, R.anim.activity_stay_anim)
            }

        })

        recycler_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        (recyclerView.layoutManager as? GridLayoutManager)?.let { gridLayoutManager ->
                            val lastItemPosition =
                                gridLayoutManager.findLastCompletelyVisibleItemPosition() + 1

                            val restItemSize = mAdapter.itemCount - lastItemPosition

                            if (restItemSize <= 8) {
                                loadProductList(mCurrentPage + 1)
                            }

                        }
                    }
                }

            })
        }

        mSearchKeyWord = findViewById<AppCompatEditText>(R.id.input_keyword).apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        loadProductList()
                        hideSoftInput(this)
                        true
                    }
                    else -> false
                }
            }
        }

        mSkinFilter = findViewById<TextView>(R.id.skin_filter).apply {
            setOnClickListener {
                showFilterDialog()
            }
        }
        setFilter(mSelectedSkinType)
    }


    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)

        mViewModel.products.observe(this, Observer { mAdapter.setItems(it) })

        mViewModel.currentPage.observe(this, Observer { mCurrentPage = it })
    }


    @SuppressLint("CheckResult")
    private fun loadProductList(page: Int = 1) {

        if (page == 1) {
            mAdapter.clearItems()
        }

        mViewModel.loadProducts(mSelectedSkinType, page, mSearchKeyWord.text.toString())
    }

    //    private fun loadProductList() {
//        val request = service.getProuducts(
//            mapOf("page" to "1")
//        )
//
//        request.observe(object : Callback<ProductsResp> {
//            override fun onFailure(call: Call<ProductsResp>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<ProductsResp>, response: Response<ProductsResp>) {
//                response.body()?.let { resp ->
//                    val productList = resp.body
//                    mAdapter.setItems(productList)
//                }
//            }
//        })
//
//    }
    private fun showFilterDialog() {
        val skinTypeList = SkinType.values()
        val items = skinTypeList.map { it.label }.toTypedArray()

        AlertDialog.Builder(this).setTitle("피부 타입을 선택하세요")
            .setItems(items) { dialogInterface, index ->
                dialogInterface.dismiss()
                setFilter(skinTypeList[index])
                loadProductList()
            }.show()

    }

    private fun setFilter(skinType: SkinType) {
        mSkinFilter.text = skinType.label
        mSelectedSkinType = skinType
    }

    private fun hideSoftInput(view: View?) {
        view?.run {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

    }
}















