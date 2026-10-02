package com.example.info.products.categories

import com.example.model.state.models.CategorySource
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CategoriesViewModelFactory {
    fun create(source: CategorySource): CategoriesViewModel
}
