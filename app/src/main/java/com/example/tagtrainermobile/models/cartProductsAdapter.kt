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

class cartProductsAdapter(val context: Context,
                          val dataSource: ArrayList<Product>) : BaseAdapter() {
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
        val rowView = inflater.inflate(R.layout.list_cart_item,parent,false)

        // Get title element
        val cartItemImgId = rowView.findViewById(R.id.cartItemImgId) as ImageView

        val prodNameTextView = rowView.findViewById(R.id.prodNameLayId) as TextView

        // Get subtitle element
        val prodPriceTextView = rowView.findViewById(R.id.prodPriceLayId) as TextView

        // Get detail element
        val prodQtdeTextView = rowView.findViewById(R.id.prodQuantiLayId) as TextView

        val productline = getItem(position) as Product

        val df = DecimalFormat("#.00")

        cartItemImgId.setImageDrawable(productline.prodImg.drawable)
        prodNameTextView.text = productline.name
        prodPriceTextView.text = "R$ "+df.format(productline.price).toString()
        prodQtdeTextView.text = "x"+productline.quantity.toString()

        return rowView
    }


}