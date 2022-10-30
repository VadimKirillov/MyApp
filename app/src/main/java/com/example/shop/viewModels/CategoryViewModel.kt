package com.example.shop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.models.CategoryModel
import com.example.shop.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val categories = categoryRepository.categories

    fun startInsert(nameCategory:String) {
        insert(CategoryModel(0, nameCategory))
    }

    fun startUpdateCategory(idCategory:Int, nameCategory:String) {
        updateCategory(CategoryModel(idCategory, nameCategory))
    }

    fun insert(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.insertCategory(categoryModel)
    }

    fun updateCategory(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.updateCategory(categoryModel)
    }

    fun delete(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.deleteCategory(categoryModel)
    }

    fun deleteAll() = viewModelScope.launch{

        categoryRepository.deleteAllCategories()
    }


}