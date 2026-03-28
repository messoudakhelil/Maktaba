package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {
    private val _booksList = mutableListOf(
        Book(isbn = "118-77-98-3456-8", title = "Clean Code", nbPages = 845),
        Book(isbn = "897-201-61622-4", title = "The Pragmatic Programmer", nbPages = 123),
        Book(isbn = "938-654321897547", title = "Design Patterns", nbPages = 476),
        Book(isbn = "543-76543218094", title = "Refactoring", nbPages = 190),
        Book(isbn = "765-9-45345-9876", title = "Head First Design Patterns", nbPages =170),
        Book(isbn = "112-34-5678-9012-3", title = "Introduction to Algorithms", nbPages = 1312),
        Book(isbn = "223-45-6789-0123-4", title = "Artificial Intelligence: A Modern Approach", nbPages = 1152),
        Book(isbn = "334-56-7890-1234-5", title = "Computer Networks", nbPages = 960),
        Book(isbn = "445-67-8901-2345-6", title = "Operating System Concepts", nbPages = 976),
        Book(isbn = "556-78-9012-3456-7", title = "Database System Concepts", nbPages = 1376),
        Book(isbn = "667-89-0123-4567-8", title = "Modern Software Engineering", nbPages = 320),
        Book(isbn = "778-90-1234-5678-9", title = "The Mythical Man-Month", nbPages = 322),
        Book(isbn = "889-01-2345-6789-0", title = "Code Complete", nbPages = 960)
    )


    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList)
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000)
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)

        booksFlow.tryEmit(_booksList.toList())
    }
}

