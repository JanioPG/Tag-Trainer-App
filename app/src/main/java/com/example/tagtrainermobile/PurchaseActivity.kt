package com.example.tagtrainermobile

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tagtrainermobile.models.Product
import com.example.tagtrainermobile.models.cartProductsAdapter
import java.text.DecimalFormat

class PurchaseActivity : AppCompatActivity() {

    var cartProducts = Product.SingleCart.singleCartinstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TagTrainerMobile)
        setContentView(R.layout.activity_purchase)
        cartTotalPrice()
        setRandomTransactionCode()
        setTransactionInfo()
        setProgressBar()
        displayPurchaseItems()
    }

    fun cartTotalPrice() : Double {
        var totalValue: Double = 0.0
        val totalQuantity: Int = 0
        for (i in cartProducts.indices) {
            cartProducts.get(i).quantity
            totalValue = totalValue + cartProducts.get(i).price

        }
        return totalValue
    }

    fun setRandomTransactionCode() : String {
        val numbers = (0..40103430).random()
        val character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val randChar = character.random()
        return numbers.toString()+randChar
    }

    fun setTransactionInfo() {
        val df = DecimalFormat("#.00")
        val txtTransactioId = findViewById<TextView>(R.id.transactioId)
            txtTransactioId.text = "Sua Compra: "+setRandomTransactionCode()
        val txtTransactionTotal = findViewById<TextView>(R.id.transactioTotalId)
            txtTransactionTotal.text = "Total: R$ "+df.format(cartTotalPrice())
        // tste - janio
        FirebaseEvent.firebasePurchase("Janio", cartProducts,
            txtTransactioId.text.toString().replace("Sua compra: ", ""),
            cartTotalPrice()
            )
        // End
    }

    fun setProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
            progressBar.progress = 100
    }

    fun displayPurchaseItems() {
        val purchaseTable = findViewById<ListView>(R.id.diplayPurchaseId)
        val adapter = cartProductsAdapter(this, cartProducts)
            purchaseTable.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

}