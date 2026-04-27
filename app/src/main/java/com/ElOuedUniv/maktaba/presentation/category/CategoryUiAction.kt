package com.ElOuedUniv.maktaba.presentation.category

sealed interface CategoryUiAction {
    object RefreshCategories : CategoryUiAction
    object OnBackClick : CategoryUiAction
}
