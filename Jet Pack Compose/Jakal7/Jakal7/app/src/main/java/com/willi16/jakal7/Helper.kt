package com.willi16.jakal7

import android.widget.TextView
import java.text.DecimalFormat

object Helper {
    fun TextView.formatPrice(value: Int?) {
        this.text = getCurrentIdr(java.lang.Double.parseDouble(value.toString()))
    }

    private fun getCurrentIdr(price: Double): CharSequence? {
        val format = DecimalFormat("#,###,###")
        return "IDR " + format.format(price).replace(",". toRegex(), ".")
    }
}