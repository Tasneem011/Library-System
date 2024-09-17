package com.example.LibraryManagementSystem.Controller;

import com.example.LibraryManagementSystem.Service.BorrowingRecordService;
import com.example.LibraryManagementSystem.dto.BorrowingRecordDto;
import com.example.LibraryManagementSystem.entity.BorrowingRecord;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@AllArgsConstructor // Keep this for constructor injection
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService; // final ensures immutability

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDto borrowingRecordDTO = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecordDTO);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordDto borrowingRecordDTO = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.OK).body(borrowingRecordDTO);
    }
}
