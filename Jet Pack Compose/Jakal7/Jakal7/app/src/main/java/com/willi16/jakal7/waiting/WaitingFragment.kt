package com.willi16.jakal7.waiting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.willi16.jakal7.R
import com.willi16.jakal7.databinding.FragmentWaitingBinding

class WaitingFragment : Fragment() {
    private lateinit var waitingBinding: FragmentWaitingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        waitingBinding = FragmentWaitingBinding.inflate(layoutInflater)
        return (waitingBinding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WaitingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}