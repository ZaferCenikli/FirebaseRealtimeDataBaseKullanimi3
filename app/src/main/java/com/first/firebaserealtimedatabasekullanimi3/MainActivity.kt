package com.first.firebaserealtimedatabasekullanimi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.sozluk2uygulamasi.VeritabaniYardimcisi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var kelimelerliste:ArrayList<Kelimeler>
    private lateinit var adapter: KelimelerAdapter
    private lateinit var vt: VeritabaniYardimcisi
    private lateinit var refkelimeler: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // veritabaniKopyala()

        toolbar.title ="Sozlük Uygulaması"
        setSupportActionBar(toolbar)
        rv.setHasFixedSize(true)
        rv.layoutManager= LinearLayoutManager(this@MainActivity)
        // kdi=ApiUtils.getKelimelerDaointerface()

        vt=VeritabaniYardimcisi(this)
        val db = FirebaseDatabase.getInstance()
        refkelimeler=db.getReference("kelimeler")

        //kelimelerliste =KelimelerTablosuDao().tümkelimeler(vt)
        // tumkelimeler()



        /*  kelimelerliste= ArrayList()
          var k1=Kelimeler(1,"cat","kedi")
          var k2=Kelimeler(2,"dog","köpek")
          var k3=Kelimeler(3,"hello","merhaba")

          kelimelerliste.add(k1)
          kelimelerliste.add(k2)
          kelimelerliste.add(k3)
  */

        kelimelerliste= ArrayList()
        adapter= KelimelerAdapter(this,kelimelerliste)
        rv.adapter=adapter
        tumKelimelerFirebase()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val item=menu?.findItem(R.id.ActionAra)
        val searchView=item?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        aramaFirebase(query)

        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        aramaFirebase(newText)


        return true
    }
/*    fun veritabaniKopyala(){
        val copyHelper= DatabaseCopyHelper(this)

        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/
    /* fun arama(aramaKelime:String){
         kelimelerliste =KelimelerTablosuDao().aramaYap(vt,aramaKelime)

         adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
         rv.adapter=adapter
     }*/
    /*fun tumkelimeler(){
        val url = "http://kasimadalan.pe.hu/sozluk/tum_kelimeler.php"
        val istek=StringRequest(Method.GET,url, {
            cevap->
            kelimelerliste= ArrayList()
            try {
                val Jsonobject=JSONObject(cevap)
                val kelimeler=Jsonobject.getJSONArray("kelimeler")
                for (i in 0 until kelimeler.length()){
                    val k =kelimeler.getJSONObject(i)

                    val kelime=Kelimeler(k.getInt("kelime_id"),
                        k.getString("ingilizce"),
                        k.getString("turkce"))
                    kelimelerliste.add(kelime)
                }
                adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
                rv.adapter=adapter

            }catch (e:Exception){
                e.printStackTrace()



            }

        }, {

        })
        Volley.newRequestQueue(this).add(istek)
    }
    fun kelimeAra(aramaKelime: String){
        val url = "http://kasimadalan.pe.hu/sozluk/kelime_ara.php"
        val istek=object:StringRequest(Method.POST,url,Response.Listener {
                cevap->
            kelimelerliste= ArrayList()
            try {
                val Jsonobject=JSONObject(cevap)
                val kelimeler=Jsonobject.getJSONArray("kelimeler")
                for (i in 0 until kelimeler.length()){
                    val k =kelimeler.getJSONObject(i)

                    val kelime=Kelimeler(k.getInt("kelime_id"),
                        k.getString("ingilizce"),
                        k.getString("turkce"))
                    kelimelerliste.add(kelime)
                }
                adapter= KelimelerAdapter(this@MainActivity,kelimelerliste)
                rv.adapter=adapter

            }catch (e:Exception){
                e.printStackTrace()



            }

        },Response.ErrorListener {

        }){
            override fun getParams(): MutableMap<String, String>? {
                val params=HashMap<String,String>()
                params["ingilizce"]=aramaKelime
                return params
            }
        }
        Volley.newRequestQueue(this).add(istek)
    }
*/
/*fun tumKelimeler(){
    kdi.tumkelimeler().enqueue(object : Callback<KelimelerCevap>{
        override fun onResponse(call: Call<KelimelerCevap>?, response: retrofit2.Response<KelimelerCevap>?
        ) {

            if(response!=null) {
                val liste = response.body().kelimeler
                adapter = KelimelerAdapter(this@MainActivity,liste)
                rv.adapter = adapter
            }
        }

        override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {


        }

    })

}
    fun aramayap(aramaKelime:String){
        kdi.kelimeAra(aramaKelime).enqueue(object : Callback<KelimelerCevap>{
            override fun onResponse(call: Call<KelimelerCevap>?, response: retrofit2.Response<KelimelerCevap>?
            ) {

                if(response!=null) {
                    val liste = response.body().kelimeler
                    adapter = KelimelerAdapter(this@MainActivity,liste)
                    rv.adapter = adapter
                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }

        })

    }*/

    fun tumKelimelerFirebase(){
        refkelimeler.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                kelimelerliste.clear()
                for(c in snapshot.children){


                    val kelime=c.getValue(Kelimeler::class.java)
                    if(kelime!=null){
                        kelime.kelime_id=c.key
                        kelimelerliste.add(kelime)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    fun aramaFirebase(aramaKelime:String ){
        refkelimeler.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                kelimelerliste.clear()
                for(c in snapshot.children){


                    val kelime=c.getValue(Kelimeler::class.java)
                    if(kelime!=null){
                        if(kelime.ingilizce!!.contains(aramaKelime)){
                            kelime.kelime_id=c.key
                            kelimelerliste.add(kelime)

                        }

                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}