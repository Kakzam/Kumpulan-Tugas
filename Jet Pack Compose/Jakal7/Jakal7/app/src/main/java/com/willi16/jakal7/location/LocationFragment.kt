package com.willi16.jakal7.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.willi16.jakal7.R
import com.willi16.jakal7.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {
    private lateinit var locationBinding: FragmentLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationBinding = FragmentLocationBinding.inflate(layoutInflater)
        return (locationBinding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LocationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}