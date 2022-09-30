package com.example.tagtrainermobile.models

import android.widget.ImageView


data class Product(
        var prodImg: ImageView,
        var name: String,
        var quantity: Int,
        var price: Double,
) {
    object SingleCart {
        var singleCartinstance = ArrayList<Product>()
    }
}
