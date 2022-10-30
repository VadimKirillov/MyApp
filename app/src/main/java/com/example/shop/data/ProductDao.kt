package com.example.shop.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shop.models.ProductModel

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(productModel: ProductModel)

    @Update
    suspend fun updateProduct(productModel: ProductModel)

    @Delete
    suspend fun deleteProduct(productModel: ProductModel)

    @Query("DELETE FROM product_data_table")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM product_data_table")
    fun getAllProducts(): LiveData<List<ProductModel>>

    @Query("SELECT * FROM product_data_table WHERE product_category = :nameCategory AND product_price = :priceProduct")
    fun getFilter(nameCategory:String, priceProduct:String): LiveData<List<ProductModel>>

    // @Query("SELECT * FROM product_data_table WHERE product_category = 'Одежда' AND product_price = '2000'")
    //fun getClothes(): LiveData<List<ProductModel>>

    // @Query("SELECT * FROM product_data_table WHERE product_category = :nameCategory OR product_price = :price")
    //fun getThreeVariant(nameCategory:String, price:String): LiveData<List<ProductModel>>
}