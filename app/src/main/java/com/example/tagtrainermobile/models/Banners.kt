package com.example.tagtrainermobile.models

import android.widget.ImageView

data class Banners(
    var bannerImg: ImageView,
    val id: Int,
    val position: String,
    val promotion_name: String
) {
    object SingleBanner {
        var singleBannerInstance = ArrayList<Banners>()
    }
}