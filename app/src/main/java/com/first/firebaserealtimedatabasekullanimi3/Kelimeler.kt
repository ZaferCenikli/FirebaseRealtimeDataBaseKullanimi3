package com.first.firebaserealtimedatabasekullanimi3

import com.google.firebase.database.IgnoreExtraProperties
//import com.google.gson.annotations.Expose
//import com.google.gson.annotations.SerializedName
//retrofit tarzında kullanım için örnek
/*data class Kelimeler(@SerializedName("kelime_id")
                     @Expose var kelime_id:Int,
                     @SerializedName("ingilizce") @Expose
                     var ingilizce:String,
                     @SerializedName("turkce")
                     @Expose var turkce:String)
    : java.io.Serializable {
}*/
//firebase tarzı kullanım için örnek
@IgnoreExtraProperties
data class Kelimeler(var kelime_id:String?=""
                     ,var ingilizce:String?=""
                     ,var turkce:String?=""):java.io.Serializable{
}