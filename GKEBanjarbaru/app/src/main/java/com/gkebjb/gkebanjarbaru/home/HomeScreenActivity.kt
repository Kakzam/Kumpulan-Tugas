package com.gkebjb.gkebanjarbaru.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.gkebjb.gkebanjarbaru.R
import com.gkebjb.gkebanjarbaru.fitur.BerandaFragment

import com.gkebjb.gkebanjarbaru.databinding.ActivityHomeScreenBinding
import com.gkebjb.gkebanjarbaru.fitur.BeritaFragment
import com.gkebjb.gkebanjarbaru.fitur.KolekteFragment
import com.gkebjb.gkebanjarbaru.fitur.ProfilFragment

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        replaceFragment(BerandaFragment.newInstance())
        homeBinding.bottomNavigation.show(0)
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_beranda,))
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_berita))
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_kolekte))
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_profil))

        homeBinding.bottomNavigation.setOnClickMenuListener {
            when(it.id) {
                0 -> {
                    replaceFragment(BerandaFragment.newInstance())
                }
                1 -> {
                    replaceFragment(BeritaFragment.newInstance())
                }
                2 -> {
                    replaceFragment(KolekteFragment.newInstance())
                }
                3 -> {
                    replaceFragment(ProfilFragment.newInstance())
                }
            }
        }
    }

    private fun replaceFragment (fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
    }
}