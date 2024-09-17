package com.example.LibraryManagementSystem.Repository;

import com.example.LibraryManagementSystem.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
