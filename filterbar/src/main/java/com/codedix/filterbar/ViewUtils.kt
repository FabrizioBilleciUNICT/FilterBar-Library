package com.codedix.filterbar

import android.content.res.Resources

class ViewUtils {

    companion object {
        fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
    }
}