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
            //teste - start
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

    fun firebaseSelectPromotion(promotion_name: String, promotion_id: String) {
        init()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION) {
            param(FirebaseAnalytics.Param.PROMOTION_ID, promotion_id)
            param(FirebaseAnalytics.Param.PROMOTION_NAME, promotion_name)
        }
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

    fun firebaseSelectContent(contentType: String, itemID: String) {

    }

    fun recordScreenView() {
        init()
        val screen_name = "Tela Teste"
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CartActivity_MyTeste")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
// remover
    //object Constants {
    //    const val LABEL = "label"
    //    const val ACTION = "action"
    //    const val CATEGORY = "category"
    //    const val VIEW = "view"
    //    const val IMPERSONATED = "impersonated"
    //    const val SELECT_CONTENT = FirebaseAnalytics.Event.SELECT_CONTENT
    //    const val VIEW_ITEM_LIST = FirebaseAnalytics.Event.VIEW_ITEM_LIST
    //    const val SELECT_PROMOTION = FirebaseAnalytics.Event.SELECT_PROMOTION
    //    const val VIEW_PROMOTION = FirebaseAnalytics.Event.VIEW_PROMOTION

//    }
}