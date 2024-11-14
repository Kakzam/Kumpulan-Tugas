package com.gkebjb.gkebanjarbaru.fitur

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gkebjb.gkebanjarbaru.databinding.FragmentKolekteBinding

class KolekteFragment : Fragment() {
    private lateinit var kolekteBinding: FragmentKolekteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kolekteBinding = FragmentKolekteBinding.inflate(layoutInflater)
        kolekteBinding.textCopy.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("empty", "106001001730536")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Rekening Sudah di copy", Toast.LENGTH_LONG).show()
        }
        return (kolekteBinding.root)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            KolekteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}