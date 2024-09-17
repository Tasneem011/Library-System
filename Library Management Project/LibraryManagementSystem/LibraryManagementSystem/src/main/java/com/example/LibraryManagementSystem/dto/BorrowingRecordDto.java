package com.example.LibraryManagementSystem.dto;


import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Patron;
import lombok.Data;

@Data
public class BorrowingRecordDto {
    private Long id;
    private Book book;
    private Patron patron;

}
