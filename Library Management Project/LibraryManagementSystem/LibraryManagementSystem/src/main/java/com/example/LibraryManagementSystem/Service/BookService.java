package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.Repository.BookRepository;
import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    @Cacheable("books")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "books", key = "#id")
    public BookDto getBookById(Long id)  {
        return bookRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @CachePut(value = "books", key = "#result.id")
    public BookDto addBook(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    @Transactional
    @CachePut(value = "books", key = "#id")
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        // Update book properties from bookDto
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setIsbn(bookDto.getIsbn());

        // Save the updated book
        Book updatedBook = bookRepository.save(book);
        return convertToDto(updatedBook);
    }

    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }


    private BookDto convertToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPublicationYear(book.getPublicationYear());
        bookDto.setIsbn(book.getIsbn());
        return bookDto;
    }

    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublicationYear(bookDto.getPublicationYear());
        book.setIsbn(bookDto.getIsbn());
        return book;
    }
}
