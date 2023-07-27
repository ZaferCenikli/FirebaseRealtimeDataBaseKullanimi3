package com.first.firebaserealtimedatabasekullanimi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detay.*

class DetayActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)

          val kelime=intent.getSerializableExtra("nesne") as Kelimeler

           textView.text=kelime.ingilizce
          textView2.text=kelime.turkce





    }
}