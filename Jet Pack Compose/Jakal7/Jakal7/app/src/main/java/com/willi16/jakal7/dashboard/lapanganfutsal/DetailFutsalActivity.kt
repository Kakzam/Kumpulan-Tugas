package com.willi16.jakal7.dashboard.lapanganfutsal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.willi16.jakal7.Helper.formatPrice
import com.willi16.jakal7.R
import com.willi16.jakal7.dashboard.model.LapanganFutsal
import com.willi16.jakal7.databinding.ActivityDetailFutsalBinding

class DetailFutsalActivity : AppCompatActivity() {
    private lateinit var detailFutsalBinding: ActivityDetailFutsalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailFutsalBinding = ActivityDetailFutsalBinding.inflate(layoutInflater)
        setContentView(detailFutsalBinding.root)

        val futsal = intent.getParcelableExtra<LapanganFutsal>("futsal")

        Glide.with(this)
            .load(futsal?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(detailFutsalBinding.ivposterDetail)

        Glide.with(this)
            .load(futsal?.gambar1)
            .placeholder(R.drawable.progress_animation)
            .into(detailFutsalBinding.gambar1)

        Glide.with(this)
            .load(futsal?.gambar2)
            .placeholder(R.drawable.progress_animation)
            .into(detailFutsalBinding.gambar2)

        Glide.with(this)
            .load(futsal?.gambar3)
            .placeholder(R.drawable.progress_animation)
            .into(detailFutsalBinding.gambar3)

        detailFutsalBinding.tvlokasiDetail.text = futsal?.alamat
        detailFutsalBinding.tvratingDetail.text = futsal?.rating
        detailFutsalBinding.tvDesc.text = futsal?.desc

        detailFutsalBinding.tvhargaDetail.formatPrice(futsal?.price)
        detailFutsalBinding.tvLapanganFutsalDetail.text = futsal?.category

        detailFutsalBinding.back.setOnClickListener{
            finish()
        }
        detailFutsalBinding.btnBooking.setOnClickListener{
            val intent = Intent(this, CheckInActivity::class.java)
                .putExtra("checkin", futsal)
            startActivity(intent)
        }
    }
}