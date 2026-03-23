package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

/**
 * Repository for managing book data
 * This follows the Repository pattern to abstract data sources
 */
class BookRepository {

    /**
     * TODO for Students (TP1 - Exercise 1):
     * Complete the book information for each book in the list below.
     * Add the following information for each book:
     * - isbn: Use a valid ISBN-13 format (e.g., "978-3-16-148410-0")
     * - nbPages: Add the actual number of pages
     *
     * Example:
     * Book(
     *     isbn = "978-0-13-468599-1",
     *     title = "Clean Code",
     *     nbPages = 464
     * )
     */
    private val booksList = listOf(
        Book(isbn = "123456", title = "Clean Code", nbPages = 5600),
        Book(isbn = "23456", title = "The Pragmatic Programmer", nbPages = 230),
        Book(isbn = "34567", title = "Design Patterns", nbPages = 500),
        Book(isbn = "45678", title = "Refactoring", nbPages = 340),
        Book(isbn = "56789", title = "Head First Design Patterns", nbPages = 400),
        Book(isbn = "67890", title = "C++", nbPages = 200),
        Book(isbn = "09877", title = "JAVA Eduction", nbPages = 100),
        Book(isbn = "98765", title = "Python", nbPages = 300),
        Book(isbn = "87654", title = "GL", nbPages = 450),
        Book(isbn = "76543", title = "SYSTEM ", nbPages = 280),
        Book(isbn = "65432", title = "LOGIC", nbPages = 320),

        )

    /**
     * TODO for Students (TP1 - Exercise 2):
     * Add 5 more books to the list above.
     * Choose books related to Computer Science, Programming, or any topic you like.
     * Remember to include complete information (ISBN, title, nbPages).
     *
     * Tip: You can find ISBN numbers for books on:
     * - Google Books
     * - Amazon
     * - GoodReads
     */

    /**
     * Get all books from the repository
     * @return List of all books
     */
    fun getAllBooks(): List<Book> {
        return booksList
    }

    /**
     * Get a book by ISBN
     * @param isbn The ISBN of the book to find
     * @return The book if found, null otherwise
     */
    fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}
