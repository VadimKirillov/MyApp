package com.example.shop.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.models.ProductModel
import com.example.shop.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel (private val productRepository: ProductRepository) : ViewModel() {

    val products = productRepository.products


    fun getFilter (nameCategory:String, priceProduct:String):
            LiveData<List<ProductModel>> {
        return productRepository.getFilter(nameCategory, priceProduct)
    }

    fun startInsert(nameProduct:String, categoryProduct:String, priceProduct:String) {
        insertProduct(ProductModel(0,nameProduct, categoryProduct, priceProduct))
    }

    fun startUpdateProduct(idProduct:Int, nameProduct:String, nameCategory:String, priceProduct:String) {
        updateProduct(ProductModel(idProduct, nameProduct, nameCategory, priceProduct))
    }

    fun insertProduct(productModel: ProductModel) = viewModelScope.launch{

        productRepository.insertProduct(productModel)
    }

    fun updateProduct(productModel: ProductModel) = viewModelScope.launch{

        productRepository.updateProduct(productModel)
    }

    fun deleteProduct(productModel: ProductModel) = viewModelScope.launch{

        productRepository.deleteProduct(productModel)
    }

    fun deleteAllProducts() = viewModelScope.launch{

        productRepository.deleteAllProducts()
    }


}