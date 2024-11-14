package com.willi16.jakal7.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.willi16.jakal7.R
import com.willi16.jakal7.dashboard.lapanganbasket.AdapterBasket
import com.willi16.jakal7.dashboard.lapanganbasket.DetailBasketActivity
import com.willi16.jakal7.dashboard.lapanganfutsal.AdapterFutsal
import com.willi16.jakal7.dashboard.lapanganfutsal.DetailFutsalActivity
import com.willi16.jakal7.dashboard.model.ImageData
import com.willi16.jakal7.dashboard.model.LapanganBasket
import com.willi16.jakal7.dashboard.model.LapanganFutsal
import com.willi16.jakal7.dashboard.profile.ProfileActivity
import com.willi16.jakal7.databinding.FragmentDashboardBinding
import com.willi16.jakal7.home.ProggresDialog

class DashboardFragment : Fragment() {
    private var imageList = ArrayList<ImageData>()
    private var dataFutsal = ArrayList<LapanganFutsal>()
    private var dataBasket = ArrayList<LapanganBasket>()
    private lateinit var adapterFutsal: AdapterFutsal
    private lateinit var adapterBasket: AdapterBasket
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: ImageAdapter
    private lateinit var dashboardBinding: FragmentDashboardBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = FragmentDashboardBinding.inflate(layoutInflater)
        return (dashboardBinding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLapangan()
        auth = FirebaseAuth.getInstance()

        Glide.with(requireContext())
            .load(auth.currentUser?.photoUrl)
            .placeholder(R.drawable.progress_animation)
            .into(dashboardBinding.ivProfile)

        imageList.add(
            ImageData(
                "https://firebasestorage.googleapis.com/v0/b/proyek1-1137c.appspot.com/o/IKLAN%201.png?alt=media&token=76b2840c-b343-4487-aa22-1de1928c489b"
        )
        )

        imageList.add(
            ImageData(
                "https://firebasestorage.googleapis.com/v0/b/proyek1-1137c.appspot.com/o/IKLAN%202.png?alt=media&token=f081b913-41c2-47e6-91d4-906e7090a0ac"
            )
        )

        adapter = ImageAdapter(imageList)
        dashboardBinding.viewPager.adapter = adapter

        dashboardBinding.ivProfile.setOnClickListener{
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        adapterFutsal = AdapterFutsal(dataFutsal) {
            val intent = Intent(requireContext(), DetailFutsalActivity::class.java)
                .putExtra("futsal", it)
            startActivity(intent)
        }

        adapterBasket = AdapterBasket(dataBasket) {
            val intent = Intent(requireContext(), DetailBasketActivity::class.java)
                .putExtra("basket", it)
            startActivity(intent)
        }


        dashboardBinding.rvLapanganfutsal.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        dashboardBinding.rvLapanganbasket.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun getLapangan() {
        val show = ProggresDialog(requireActivity())
        show.showLoading()
        databaseReference = FirebaseDatabase.getInstance().getReference("Dashboard")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataFutsal.clear()
                for (item in snapshot.child("LapanganFutsal").children){
                    val futsal = item.getValue(LapanganFutsal::class.java)
                    dataFutsal.add(futsal!!)
                }
                dashboardBinding.rvLapanganfutsal.adapter = adapterFutsal

                dataBasket.clear()
                for (item in snapshot.child("LapanganBasket").children){
                    val  basket = item.getValue(LapanganBasket::class.java)
                    dataBasket.add(basket!!)
                }
                dashboardBinding.rvLapanganbasket.adapter = adapterBasket
                show.dismissLoading()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message,Toast.LENGTH_SHORT).show()
                show.showLoading()
            }

        })
    }
}