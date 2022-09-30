package com.example.tagtrainermobile

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.tagtrainermobile.models.ListProductsAdapter
import com.example.tagtrainermobile.models.ListingProduct


class MainActivity : AppCompatActivity() {

    var listingProducts = ListingProduct.SingleList.singleListInstance


    fun onClickedProducts(v: ListView, p: Int) {
        val intent = Intent(applicationContext, ProductActivity::class.java)

        val params = Bundle()
        params.putInt("id", p)
        intent.putExtras(params)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TagTrainerMobile)
        setContentView(R.layout.activity_main)
        displayListingPage()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val menu = findViewById<SearchView>(R.id.searchViewId)

        menu.setSearchableInfo(searchManager.getSearchableInfo(componentName))

    }

    fun filteredProductsList() : ArrayList<ListingProduct> {
        val listCategory = intent.getStringExtra("listType")

        val categoryList = ArrayList<ListingProduct>()
            for(i in listingProducts) {
                if(i.listProdCat == listCategory) {
                    categoryList.add(i)
                }
            }
            if (categoryList.size <= 0) return listingProducts
        return categoryList
    }

    fun displayListingPage() {
        val table: ListView = findViewById(R.id.tableID)
        val adapter = ListProductsAdapter(this, filteredProductsList())
        table.adapter = adapter
            table.setOnItemClickListener { parent, view, position, id ->
                onClickedProducts(table, filteredProductsList().get(position).listProdId-1)
            }
    }
}

