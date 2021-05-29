package com.example.fintonicbeer.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Beer(
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val beerId: String,

    @ColumnInfo(name = "beer_name")
    @SerializedName("name")
    val beerName: String,

    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    val tagLine: String,

    @ColumnInfo(name = "first_brewed")
    @SerializedName("first_brewed")
    val firstBrewed: String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String,

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "abv")
    @SerializedName("abv")
    val alcoholVolumen: String,

    @ColumnInfo(name = "brewers_tips")
    @SerializedName("brewers_tips")
    val brewersTips: String,

    @ColumnInfo(name = "food_pairing")
    @SerializedName("food_pairing")
    val foodPairing: List<String>,

    ) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class BeerColorPalette(var color: Int)