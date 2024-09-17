package com.example.LibraryManagementSystem.Repository;

import com.example.LibraryManagementSystem.entity.BorrowingRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {


    Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);
}
