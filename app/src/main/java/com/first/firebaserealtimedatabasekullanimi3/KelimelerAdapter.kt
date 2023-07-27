package com.first.firebaserealtimedatabasekullanimi3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class KelimelerAdapter(val context: Context, private val kelimeListe:List<Kelimeler>)
    :RecyclerView.Adapter<KelimelerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(tasarim: View): RecyclerView.ViewHolder(tasarim){



        var kelime_card: CardView
        var textViewingilizce: TextView
        var textViewturkce: TextView


        init {
            kelime_card=tasarim.findViewById(R.id.cardView1)
            textViewingilizce=tasarim.findViewById(R.id.textViewingilizce)
            textViewturkce=tasarim.findViewById(R.id.textViewturkce)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim= LayoutInflater.from(context).inflate(R.layout.card_tasarim,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {

        return kelimeListe.size



    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val kelime=kelimeListe.get(position)
        holder.textViewingilizce.text=kelime.ingilizce
        holder.textViewturkce.text=kelime.turkce
        holder.kelime_card.setOnClickListener {
          //  ıntent.putExtra("nesne",kelime)
        //    context.startActivity(ıntent)
            val ıntent=Intent(context,DetayActivity::class.java)
            ıntent.putExtra("nesne",kelime)
            context.startActivity(ıntent)

        }

    }
}