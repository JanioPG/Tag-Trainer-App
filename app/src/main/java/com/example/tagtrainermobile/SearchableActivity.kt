package com.example.tagtrainermobile

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tagtrainermobile.models.ListProductsAdapter
import com.example.tagtrainermobile.models.ListingProduct

var listingProducts = ListingProduct.SingleList.singleListInstance
var searchedItens = ArrayList<ListingProduct>()


class SearchableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TagTrainerMobile)
        setContentView(R.layout.activity_searchable)
        searchedItens.clear()

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
            doMySearch(query)
                setSearcheableConfiguration(query)
            }
        }
    }

    fun onClickedProducts(v: ListView, p: Int) {
        val intent = Intent(applicationContext, ProductActivity::class.java)

        val params = Bundle()
        params.putInt("id", p-1)
        intent.putExtras(params)

        startActivity(intent)
    }

    fun doMySearch(query : String) : ArrayList<ListingProduct>? {
        val allSearchProducts = ArrayList<ListingProduct>()
        for (List in listingProducts) {
            if (List.listProdId.toString() == query) {
                allSearchProducts.add(List)
            } else if (List.listProdName.contains(query, ignoreCase = true)) {
                allSearchProducts.add(List)
            } else if (List.listProdDesc.contains(query, ignoreCase = true)) {
                allSearchProducts.add(List)
            }
        }
        return allSearchProducts
    }

    fun setSearcheableConfiguration(query : String) {
        val searchResult = findViewById<TextView>(R.id.searchResultsId)
            searchResult.text = "Resultado de Busca: "+query
        val searchTable = findViewById<ListView>(R.id.tableResultsId)
        val productResult = doMySearch(query)
        if (productResult?.size!! > 0) {
            val adapter = ListProductsAdapter(this, productResult)
            searchTable.adapter = adapter
            searchTable.setOnItemClickListener { parent, view, position, id ->
                onClickedProducts(searchTable, productResult.get(position).listProdId)
            }

        } else {
            val prodNotFoundId = findViewById<TextView>(R.id.prodNotFoundId)
                prodNotFoundId.visibility = View.VISIBLE
                prodNotFoundId.text = "Ops!! Produto não Encontrado!!"
        }

    }
}