package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase

import kotlinx.coroutines.flow.MutableSharedFlow
@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {



    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            getBooksUseCase().collect { books ->

                _uiState.update {
                    it.copy(
                        books = books,
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Exercise 3 - Handle UI Actions
     */
    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.RefreshBooks -> {
                refreshBooks()
            }
            BookUiAction.OnAddBookClick -> {
                _uiState.update {
                    it.copy(isAddingBook = true)
                }
            }
            BookUiAction.OnDismissAddBook -> {
                _uiState.update {
                    it.copy(isAddingBook = false)
                }
            }
            is BookUiAction.OnAddBookConfirm -> {
                viewModelScope.launch {

                    val newBook = Book(
                        isbn = action.isbn,
                        title = action.title,
                        nbPages = action.nbPages
                    )

                    addBookUseCase(newBook)

                    _uiState.update { current ->
                        current.copy(
                            books = current.books + newBook, // أضف الكتاب للقائمة الحالية
                            isAddingBook = false
                        )
                    }
                }
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }
}

