package com.ElOuedUniv.maktaba.presentation.category

sealed interface CategoryUiEvent {
    data class ShowSnackbar(val message: String) : CategoryUiEvent
    object NavigateBack : CategoryUiEvent
}
