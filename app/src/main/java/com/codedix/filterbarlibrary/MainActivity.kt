package com.codedix.filterbarlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.codedix.filterbar.FilterBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val rl = findViewById<RelativeLayout>(R.id.rl_filterbar)
        val fb = FilterBar(this, true, listOf("Aaa", "Bbb", "Ccc"), arrayListOf("Aaa"), "Filters")
        rl.addView(fb)

        fb.filterUpdated = {
            System.err.println(it)
        }
    }
}
