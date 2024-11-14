package com.gkebjb.gkebanjarbaru.fitur

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gkebjb.gkebanjarbaru.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {
    private lateinit var profilBinding: FragmentProfilBinding
    private lateinit var buttonPeta: Button
    private lateinit var buttonWebsite: Button
    private lateinit var buttonEmail: Button
    private lateinit var buttonYoutube: Button
    private lateinit var buttonFacebook: Button
    private lateinit var buttonInstagram: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profilBinding = FragmentProfilBinding.inflate(layoutInflater)
        return (profilBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonPeta = profilBinding.buttonPeta
        val buttonWebsite = profilBinding.buttonWebsite
        val buttonEmail = profilBinding.buttonEmail
        val buttonYoutube = profilBinding.buttonYoutube
        val buttonFacebook = profilBinding.buttonFacebook
        val buttonInstagram = profilBinding.buttonInstagram

        buttonPeta.setOnClickListener {
            // Handle button click for Peta
            // Contoh: startActivity(Intent(requireContext(), PetaActivity::class.java))
        }

        buttonWebsite.setOnClickListener {
            openUrlInBrowser("https://gkebjb.blogspot.com")
        }

        buttonEmail.setOnClickListener {
            // Handle button click for Website
            // Contoh: openUrlInBrowser("https://gkebjb.blogspot.com")
        }

        buttonYoutube.setOnClickListener {
            openUrlInBrowser("https://www.youtube.com/@GKEBanjarbaru")
        }

        buttonFacebook.setOnClickListener {
            // Handle button click for Website
            // Contoh: openUrlInBrowser("https://gkebjb.blogspot.com")
        }

        buttonInstagram.setOnClickListener {
            // Handle button click for Website
            // Contoh: openUrlInBrowser("https://gkebjb.blogspot.com")
        }

    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfilFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}