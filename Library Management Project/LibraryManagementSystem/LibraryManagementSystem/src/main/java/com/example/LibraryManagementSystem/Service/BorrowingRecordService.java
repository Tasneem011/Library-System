package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.Repository.BookRepository;
import com.example.LibraryManagementSystem.Repository.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.Repository.PatronRepository;
import com.example.LibraryManagementSystem.dto.BorrowingRecordDto;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.BorrowingRecord;
import com.example.LibraryManagementSystem.entity.Patron;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor  // Lombok annotation to generate a constructor with all final fields
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id " + patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());


        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        return convertToDTO(savedRecord);
    }

    @Transactional
    public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
        // Retrieve the BorrowingRecord from the database
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with bookId " + bookId + " and patronId " + patronId));

        // Ensure no previous return date is set if not expected
        if (record.getReturnDate() != null) {
            throw new IllegalStateException("Book has already been returned.");
        }

        record.setReturnDate(LocalDate.now());
        BorrowingRecord updatedRecord = borrowingRecordRepository.save(record);
        return convertToDTO(updatedRecord);
    }

    private BorrowingRecordDto convertToDTO(BorrowingRecord borrowingRecord) {
        return modelMapper.map(borrowingRecord, BorrowingRecordDto.class);
    }

    private BorrowingRecord convertToEntity(BorrowingRecordDto borrowingRecordDTO) {
        return modelMapper.map(borrowingRecordDTO, BorrowingRecord.class);
    }
}

