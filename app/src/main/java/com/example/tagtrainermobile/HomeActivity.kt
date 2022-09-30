package com.example.tagtrainermobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tagtrainermobile.models.Banners
import com.example.tagtrainermobile.models.ListingProduct
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    var bannersHome = Banners.SingleBanner.singleBannerInstance
    var listingProducts = ListingProduct.SingleList.singleListInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)

        setTheme(R.style.Theme_TagTrainerMobile)

        setContentView(R.layout.activity_home)

        setListProducts()
        setHomeBanners()
        homeClickButton()
        setHomeCards()
        FirebaseEvent.recordScreenView()
    }

    fun setListProducts() {
        if(listingProducts.size <= 0) {
            val imageView0 = ImageView(this)
            imageView0.setImageResource(R.drawable.p0)
            val prod0 = ListingProduct(imageView0, 1, "Uma fragrância sensual e vibrante perfeita para momentos especiais.", "Luna Radiante", 89.90, "Principal")
            listingProducts.add(prod0)
            val imageView1 = ImageView(this)
            imageView1.setImageResource(R.drawable.p1)
            val prod1 = ListingProduct(imageView1, 2, "Todo mundo tem um jeito único de se perfumar. Mas para aproveitar todo o potencial da fragrância", "Essencial Masculino", 129.90, "Principal")
            listingProducts.add(prod1)
            val imageView2 = ImageView(this)
            imageView2.setImageResource(R.drawable.p2)
            val prod2 = ListingProduct(imageView2, 3, "Desodorante Colônia Kaiak Urbe Masculino - 100ml", "Kaiak Urbe", 94.90, "Principal")
            listingProducts.add(prod2)
            val imageView3 = ImageView(this)
            imageView3.setImageResource(R.drawable.bp1)
            val prod3 = ListingProduct(imageView3, 4, "Aproveite os nossos queridinhos de hidratação e limpeza para intensificar a sua rotina", "Presente MaisQuerido", 79.90, "Presentes")
            listingProducts.add(prod3)
            val imageView4 = ImageView(this)
            imageView4.setImageResource(R.drawable.bp2)
            val prod4 = ListingProduct(imageView4, 5, "O Presente Natura Homem Miniaturas possui fragrância amadeirada moderada com o frescor das ervas aromáticas", "Presente Homem", 52.90, "Presentes")
            listingProducts.add(prod4)
            val imageView5 = ImageView(this)
            imageView5.setImageResource(R.drawable.bp3)
            val prod5 = ListingProduct(imageView5, 6, "Nutrir a pele, alimentamos também o nosso sentir. Por isso, desenvolveu produtos ultra hidratantes", "Presente TodoDia", 34.90, "Presentes")
            listingProducts.add(prod5)
        } else return
    }

    fun timerBanners() {
        val pager = findViewById<ViewPager>(R.id.homeBannerId)
        if(pager.currentItem == 0) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    pager.post(Runnable {
                        pager.setCurrentItem(pager.currentItem + 1, true)
                        return@Runnable
                    })
                }
            }, 5000)
        } else {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    pager.post(Runnable {
                        pager.setCurrentItem(pager.currentItem - 1,true)
                        return@Runnable
                    })
                }
            }, 5000)
        }
    }

    fun setHomeBanners() {
        val pager = findViewById<ViewPager>(R.id.homeBannerId)
        pager.adapter = homeBannerAdapter(bannersHome)

        pager.post(Runnable { timerBanners() })
            pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    timerBanners()
                }

                override fun onPageSelected(position: Int) {
                    return
                }

                override fun onPageScrollStateChanged(state: Int) {
                    return
                }
            })

        if(bannersHome.size <= 0) {
            val bannerImg0 = ImageView(this)
            bannerImg0.setImageResource(R.drawable.b1)
            val banner0 = Banners(bannerImg0, 1, "top_banner_1", "Principal")
            bannersHome.add(banner0)
            val bannerImg1 = ImageView(this)
            bannerImg1.setImageResource(R.drawable.b2)
            val banner1 = Banners(bannerImg1, 2, "top_banner_2", "Presentes")
            bannersHome.add(banner1)
        } else return
    }

    private inner class homeBannerAdapter(val banners : ArrayList<Banners>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View = layoutInflater.inflate(R.layout.banner_pager_item,container, false) as ViewGroup
            val teste = view.findViewById<ViewPager>(R.id.bannerPagerId) as ImageView
                teste.setImageDrawable(banners[position].bannerImg.drawable)
                teste.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        onClickBannerAction(banners[position].promotion_name)
                        // Teste - Janio
                        FirebaseEvent.firebaseSelectPromotion(banners[position].promotion_name, banners[position].position)
                        // End
                    }
                })
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int = 2

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

    }

    private inner class homeProductsAdapter(val dataSource: ArrayList<ListingProduct>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View = layoutInflater.inflate(R.layout.card_view_home_item, container, false) as ViewGroup

            val firstProductCard = dataSource[position]
            val homeCardImgId = view.findViewById<ImageView>(R.id.homeCardImgId)
            val prodNameCardId = view.findViewById<TextView>(R.id.prodNameCardId)
            val prodPriceCardId = view.findViewById<TextView>(R.id.prodPriceCardId)
            val buttonBuyCardId = view.findViewById<Button>(R.id.buttonBuyCardId)
            homeCardImgId.setImageDrawable(firstProductCard.listProdImg.drawable)
            prodNameCardId.text = firstProductCard.listProdName
            val df = DecimalFormat("#.00")
            prodPriceCardId.text = "R$ "+df.format(firstProductCard.listProdPrice).toString()
            buttonBuyCardId.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    onClickCardAction(firstProductCard.listProdId)
                }
            })

            val secondProductCard = dataSource[position+3]
            val homeCardImgId2 = view.findViewById<ImageView>(R.id.homeCardImgId2)
            val prodNameCardId2 = view.findViewById<TextView>(R.id.prodNameCardId2)
            val prodPriceCardId2 = view.findViewById<TextView>(R.id.prodPriceCardId2)
            val buttonBuyCardId2 = view.findViewById<Button>(R.id.buttonBuyCardId2)
            homeCardImgId2.setImageDrawable(secondProductCard.listProdImg.drawable)
            prodNameCardId2.text = secondProductCard.listProdName
            val df1 = DecimalFormat("#.00")
            prodPriceCardId2.text = "R$ " + df1.format(secondProductCard.listProdPrice).toString()
            buttonBuyCardId2.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    onClickCardAction(secondProductCard.listProdId)
                }
            })
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return 3
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
    }

    fun homeClickButton() {
        val button = findViewById<Button>(R.id.homeButtonId)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    fun onClickBannerAction(p: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)

        val params = Bundle()
        params.putString("listType", p)
        intent.putExtras(params)

        startActivity(intent)
    }

    fun setHomeCards() {
        val card1 = findViewById<ViewPager>(R.id.pagerCardsHomeId)
        card1.adapter = homeProductsAdapter(listingProducts)
    }

    fun  onClickCardAction(p: Int) {
        val intent = Intent(applicationContext, ProductActivity::class.java)

        val params = Bundle()
        params.putInt("id", p-1)
        intent.putExtras(params)

        startActivity(intent)
    }
}