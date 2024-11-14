package com.willi16.jakal7.dashboard.lapanganbasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.willi16.jakal7.Helper.formatPrice
import com.willi16.jakal7.R
import com.willi16.jakal7.dashboard.model.LapanganFutsal
import com.willi16.jakal7.databinding.ActivityDetailBasketBinding

class DetailBasketActivity : AppCompatActivity() {
    private lateinit var detailBasketBinding: ActivityDetailBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBasketBinding = ActivityDetailBasketBinding.inflate(layoutInflater)
        setContentView(detailBasketBinding.root)

        val basket = intent.getParcelableExtra<LapanganFutsal>("basket")

        Glide.with(this)
            .load(basket?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(detailBasketBinding.ivposterDetail)

        Glide.with(this)
            .load(basket?.gambar1)
            .placeholder(R.drawable.progress_animation)
            .into(detailBasketBinding.gambar1)

        Glide.with(this)
            .load(basket?.gambar2)
            .placeholder(R.drawable.progress_animation)
            .into(detailBasketBinding.gambar2)

        Glide.with(this)
            .load(basket?.gambar3)
            .placeholder(R.drawable.progress_animation)
            .into(detailBasketBinding.gambar3)

        detailBasketBinding.tvlokasiDetail.text = basket?.alamat
        detailBasketBinding.tvratingDetail.text = basket?.rating
        detailBasketBinding.tvDesc.text = basket?.desc

        detailBasketBinding.tvhargaDetail.formatPrice(basket?.price)
        detailBasketBinding.tvLapanganBasketDetail.text = basket?.category

        detailBasketBinding.back.setOnClickListener{
            finish()
        }
        detailBasketBinding.btnBooking.setOnClickListener{

        }
    }
}