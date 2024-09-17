package com.example.LibraryManagementSystem.Controller;

import com.example.LibraryManagementSystem.Service.BookService;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Validated
public class BookController {
    private BookService bookService;
    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id)  {
        BookDto bookDto = bookService.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }
@PostMapping("/add")
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto){
        BookDto createdBookDto = bookService.addBook(bookDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(createdBookDto);
    }
@PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id , @RequestBody BookDto bookDto)  {
        BookDto updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable long id)  {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
