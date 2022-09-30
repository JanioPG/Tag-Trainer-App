package com.example.tagtrainermobile.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tagtrainermobile.R
import java.text.DecimalFormat

class ListProductsAdapter(val context: Context,
                          val dataSource: ArrayList<ListingProduct>) : BaseAdapter () {
    private val inflater: LayoutInflater
        = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.product_list_item,parent,false)

        // Get title element
        val listingProdId = rowView.findViewById(R.id.listingProdId) as TextView

        // Get title element
        val listingProdNameId = rowView.findViewById(R.id.listingProdNameId) as TextView

        // Get subtitle element
        val listingProdDescId = rowView.findViewById(R.id.listingProdDescId) as TextView

        // Get detail element
        val listingProdPriceId = rowView.findViewById(R.id.listingProdPriceId) as TextView

        val listingProdImag  = rowView.findViewById(R.id.listingProdImgId) as ImageView

        val productline = getItem(position) as ListingProduct

        val df = DecimalFormat("#.00")

        listingProdId.text = "ID: "+productline.listProdId.toString()
        listingProdNameId.text = productline.listProdName
        listingProdDescId.text = productline.listProdDesc
        listingProdPriceId.text = "R$ "+ df.format(productline.listProdPrice).toString()
        listingProdImag.setImageDrawable(productline.listProdImg.drawable)



        return rowView
    }

}