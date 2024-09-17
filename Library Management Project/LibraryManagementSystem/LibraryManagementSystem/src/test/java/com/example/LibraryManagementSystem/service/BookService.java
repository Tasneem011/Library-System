package com.example.LibraryManagementSystem.service;



import com.example.LibraryManagementSystem.Repository.BookRepository;
import com.example.LibraryManagementSystem.Service.BookService;
import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setPublicationYear(2008);
        book.setIsbn("978-0132350884");

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Clean Code");
        bookDto.setAuthor("Robert C. Martin");
        bookDto.setPublicationYear(2008);
        bookDto.setIsbn("978-0132350884");
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<BookDto> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnBookById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        BookDto foundBook = bookService.getBookById(book.getId());

        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
        assertEquals("Clean Code", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(book.getId());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFoundById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookById(book.getId()));

        assertEquals("Book not found with ID: 1", exception.getMessage());
    }

    @Test
    void shouldAddNewBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto savedBook = bookService.addBook(bookDto);

        assertNotNull(savedBook);
        assertEquals("Clean Code", savedBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldUpdateBook() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto updatedBook = bookService.updateBook(book.getId(), bookDto);

        assertNotNull(updatedBook);
        assertEquals("Clean Code", updatedBook.getTitle());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldDeleteBook() {
        when(bookRepository.existsById(book.getId())).thenReturn(true);
        doNothing().when(bookRepository).deleteById(book.getId());

        bookService.deleteBook(book.getId());

        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingBook() {
        when(bookRepository.existsById(book.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> bookService.deleteBook(book.getId()));

        assertEquals("Book not found with ID: 1", exception.getMessage());
        verify(bookRepository, times(0)).deleteById(book.getId());
    }
}
