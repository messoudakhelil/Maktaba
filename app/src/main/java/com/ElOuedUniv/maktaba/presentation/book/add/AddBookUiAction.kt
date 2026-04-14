package com.ElOuedUniv.maktaba.presentation.book.add

sealed class AddBookUiAction {
    data class OnImageChange(val url: String) : AddBookUiAction()
    data class OnImageSelected(val uri: String) : AddBookUiAction()
    data class OnTitleChange(val title: String) : AddBookUiAction()
    data class OnIsbnChange(val isbn: String) : AddBookUiAction()
    data class OnPagesChange(val pages: String) : AddBookUiAction()
    object OnAddClick : AddBookUiAction()
}
