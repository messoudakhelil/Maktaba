package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }

            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn) }
                validateInputs()
            }
            is AddBookUiAction.OnImageSelected -> {
                _uiState.update { it.copy(imageUri = action.uri) }
            }
            is AddBookUiAction.OnImageChange -> {
                _uiState.update { it.copy(imageUrl = action.url) }
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages) }
                validateInputs()
            }

            AddBookUiAction.OnAddClick -> {
                validateInputs()
                if (_uiState.value.isFormValid) {
                    addBook()
                }
            }
        }
    }

    private fun validateInputs() {
        val current = _uiState.value

        val titleError =
            if (current.title.isBlank()) "Title cannot be empty" else null

        val isbnError =
            if (current.isbn.length != 13 || current.isbn.any { !it.isDigit() }) {
                "ISBN must be exactly 13 digits"
            } else null

        val pagesInt = current.nbPages.toIntOrNull()
        val pagesError =
            if (pagesInt == null || pagesInt <= 0) {
                "Pages must be a positive number"
            } else null

        val isValid = titleError == null && isbnError == null && pagesError == null

        _uiState.update {
            it.copy(
                titleError = titleError,
                isbnError = isbnError,
                pagesError = pagesError,
                isFormValid = isValid
            )
        }
    }

    private fun addBook() {
        val currentState = _uiState.value

        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,

            imageUrl = currentState.imageUri

        )

        addBookUseCase(book)
        _uiState.update { it.copy(isSuccess = true) }
    }
}