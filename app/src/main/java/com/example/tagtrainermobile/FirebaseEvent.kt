package com.example.tagtrainermobile

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.example.tagtrainermobile.models.ListingProduct
import com.example.tagtrainermobile.models.Product
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


object FirebaseEvent {
    // teste app CN
    private const val PROMOTION = "Promotion"
    private const val BANNER = "banner"
    // Fim teste app CN
    private var currency = "BRL"
    private var value = 0.0
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private fun init() {
        firebaseAnalytics = Firebase.analytics
    }

    fun firebasePaymentInfo(method: String) {
        init()
        value = PurchaseActivity().cartTotalPrice()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO) {
            param(FirebaseAnalytics.Param.CURRENCY, currency)
            param(FirebaseAnalytics.Param.VALUE, value)
            param(FirebaseAnalytics.Param.PAYMENT_TYPE, method)
        }
    }

    fun firebaseRemoveFromCart(product_name: String, product_price: Double) {
        init()
        val itemRemoved = Bundle().apply {
            // teste - start
            putString(FirebaseAnalytics.Param.ITEM_ID, "id_test_123")
            putString(FirebaseAnalytics.Param.ITEM_BRAND, "Marca_test_123")
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "category_test_123")
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY2, "category2_test")
            //teste - end
            putString(FirebaseAnalytics.Param.ITEM_NAME, product_name)
            putDouble(FirebaseAnalytics.Param.PRICE, product_price)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART) {
            param(FirebaseAnalytics.Param.CURRENCY, currency)
            param(FirebaseAnalytics.Param.VALUE, 1 * product_price)
            param(FirebaseAnalytics.Param.ITEMS, itemRemoved)
        }
    }

    fun firebaseSelectContent(promotion_name: String, promotion_id: String) {
        init()

        val promo1 = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, "BR123-Test")
            putString(FirebaseAnalytics.Param.ITEM_NAME, "Promtion name test")
            putString(FirebaseAnalytics.Param.CREATIVE_NAME, "test_promo.jpg")
            putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "1")
        }

        val params = Bundle().apply {
            putString("banner", "true")
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Promotion")
            putBoolean("productInTheCart", true)
            putString("directSales", "test01")
            putBoolean("loggedUser", true)
            putString("dsSpace", "test01")
            putString("dsType", "test01")
            putString("directSalesId", "ID123")
            putString("region", "BR")
            putParcelableArrayList("promotions", arrayListOf(promo1))
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
    }

    // a partir desse método, ver o encapsulamento de scripts (repetidos)
    fun firebaseAddToCart(productAdded: Product) {
        init()
        val itemAdd = Bundle().apply {
            //teste - start
            putString(FirebaseAnalytics.Param.ITEM_ID, "id_test_123")
            putString(FirebaseAnalytics.Param.ITEM_BRAND, "Marca_test_123")
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "category_test_123")
            //putString(FirebaseAnalytics.Param.ITEM_CATEGORY2, "category2_test")
            putString("dimension175", "category2_atual_23_09")
            //teste - end
            putString(FirebaseAnalytics.Param.ITEM_NAME, productAdded.name)
            putDouble(FirebaseAnalytics.Param.PRICE, productAdded.price)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART) {
            param(FirebaseAnalytics.Param.CURRENCY, currency)
            param(FirebaseAnalytics.Param.VALUE, productAdded.quantity * productAdded.price)
            param(FirebaseAnalytics.Param.ITEMS, itemAdd)
        }
    }

    // corrigir desempenho desse método
    fun firebaseViewItemList(list_name: String, list: ArrayList<ListingProduct>) {
        init()
        val products = arrayListOf<Parcelable>()
        for (product in list) {
            products.add(Bundle().apply {
                putInt(FirebaseAnalytics.Param.ITEM_ID, product.listProdId)
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.listProdName)
                putDouble(FirebaseAnalytics.Param.PRICE, product.listProdPrice)
            })
        }

        val bundle = Bundle().apply {
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, products)
            putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, list_name)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle)
    }

    fun firebaseBeginCheckout() {
        init()
        var cartProducts = Product.SingleCart.singleCartinstance
        val products = arrayListOf<Parcelable>()
        value = PurchaseActivity().cartTotalPrice()

        for (product in cartProducts) {
            products.add(Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price)
            })
        }

        val bundle = Bundle().apply {
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, products)
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, value)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle)
    }

    fun firebasePurchase(affiliation: String, cartProducts: ArrayList<Product>, transactionCode: String, totalPrice: Double) {
        init()
        val products = arrayListOf<Parcelable>()
        for (product in cartProducts) {
            products.add(Bundle().apply {
                // teste - start
                putString(FirebaseAnalytics.Param.ITEM_ID, "id_test_123")
                putString(FirebaseAnalytics.Param.ITEM_BRAND, "Marca_test_123")
                putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "category_test_123")
                putString("dimension175", "category2_test")
                //putString(FirebaseAnalytics.Param.ITEM_CATEGORY2, "category2_test")
                // teste - end
                putString(FirebaseAnalytics.Param.ITEM_NAME, product.name)
                putDouble(FirebaseAnalytics.Param.PRICE, product.price / product.quantity)
                putInt(FirebaseAnalytics.Param.QUANTITY, product.quantity)
            })
        }
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.TRANSACTION_ID, transactionCode)
            putString(FirebaseAnalytics.Param.AFFILIATION, affiliation)
            putString(FirebaseAnalytics.Param.CURRENCY, currency)
            putDouble(FirebaseAnalytics.Param.VALUE, totalPrice)
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, products)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, bundle)
        firebaseAnalytics.logEvent("ecommerce_purchase" , bundle)
    }


    fun recordScreenView(screen_name: String) {
        init()
        val name = screen_name
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, name)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CartActivity_MyTeste")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun viewItem() {
        init()
        val items = arrayListOf<Bundle>()
        items.add(
            Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, "item_id_banner")
                putString(FirebaseAnalytics.Param.ITEM_NAME, "item_name_banner")
                //putString(FirebaseAnalytics.Param.CREATIVE_NAME, "creative_name_banner")
                //putInt(FirebaseAnalytics.Param.CREATIVE_SLOT, 0)
                putString("creative", "creative_name_banner")
                putInt("position", 0)
            }
        )
        val bundle = Bundle().apply {
            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, items)
            putBoolean("productInTheCart", true)
            putString("directSales", "Test")
            putBoolean("loggedUser", true)
            putString("dsSpace", "Test_dsSpace")
            putString("dsType", "Test_dsSpace")
            putString("directSalesId", "id123")
            putString("region", "BR")
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Promotion")
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)
    }
}