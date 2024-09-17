package com.example.LibraryManagementSystem.dto;


import com.example.LibraryManagementSystem.entity.BorrowingRecord;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long  id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;


}
