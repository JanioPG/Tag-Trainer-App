package com.example.tagtrainermobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tagtrainermobile.models.ListingProduct
import com.example.tagtrainermobile.models.Product
import com.google.android.material.snackbar.Snackbar

class ProductActivity : AppCompatActivity() {

    var cartProducts = Product.SingleCart.singleCartinstance
    var listingProducts = ListingProduct.SingleList.singleListInstance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TagTrainerMobile)
        setContentView(R.layout.activity_product)
        setViewProducts()
    }

    fun cartEmpty (): Boolean {
        return cartProducts.size <= 0
    }

    fun cartNotEmpty(button: ImageButton) {
        if (cartEmpty()) {
            return
        } else {
            button.visibility = ImageButton.VISIBLE
            button.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val intent = Intent(applicationContext, CartActivity::class.java)
                    startActivity(intent)
                }
            })
        }
    }

    fun setViewProducts() {
        val idProduct = intent.getIntExtra("id", 34)
        val productImage = findViewById<ImageView>(R.id.productImageSrcID)
        val textView = findViewById<TextView>(R.id.prodLabel)
        val productDesc = findViewById<TextView>(R.id.prodDescriptionId)
        val productPrice = findViewById<TextView>(R.id.prodPriceId)
        val addProdButton = findViewById<Button>(R.id.buttonAddId)
        val cartButton = findViewById<ImageButton>(R.id.cartButtonId)
        cartNotEmpty(cartButton)

        productImage.setImageDrawable(listingProducts.get(idProduct).listProdImg.drawable)
        productImage.visibility = View.VISIBLE

        val productName = listingProducts.get(idProduct).listProdName
        textView.text = productName
        val productDescText = listingProducts.get(idProduct).listProdDesc
        productDesc.text = productDescText
        val productPriceText = listingProducts.get(idProduct).listProdPrice
        productPrice.text = "R$ " + productPriceText.toString()

        addProdButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                addToCart(addProdButton, listingProducts.get(idProduct))
            }
        })
    }

    fun addToCart(v: View, p: ListingProduct ) {
        val cartButton = findViewById(R.id.cartButtonId) as ImageButton
        val productAdded = Product(p.listProdImg,p.listProdName, 1, p.listProdPrice)
        val existingProduct = cartProducts.find({ it.name == productAdded.name })
        if (existingProduct !== null) {
            cartProducts.forEach {
                if (it.name == productAdded.name) {
                    it.price = it.price + productAdded.price
                    it.quantity++
                }
            }
        } else {
            cartProducts.add(productAdded)
            cartNotEmpty(cartButton)
        }

        // Teste - Janio
        FirebaseEvent.firebaseAddToCart(productAdded)
        // End

        Snackbar.make(v, "Produto Adicionado ao carrinho: " + productAdded.name, Snackbar.LENGTH_LONG)
            .show()
    }
}