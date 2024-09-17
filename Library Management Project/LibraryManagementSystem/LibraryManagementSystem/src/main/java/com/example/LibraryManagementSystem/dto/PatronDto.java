package com.example.LibraryManagementSystem.dto;


import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowingRecord;
import lombok.Data;

import java.util.Set;

@Data
public class PatronDto {
    private Long id;
    private String name;
    private String email;
    private Set<BorrowingRecord>  borrowingRecords;
}
