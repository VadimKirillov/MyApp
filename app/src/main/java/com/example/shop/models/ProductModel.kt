package com.example.shop.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_data_table")
data class ProductModel (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    var id : Int,

    @ColumnInfo(name = "product_name")
    var name : String,

    @ColumnInfo(name = "product_category")
    var category : String,

    @ColumnInfo(name = "product_price")
    var price : String

)