package com.example.LibraryManagementSystem.Repository;

import com.example.LibraryManagementSystem.entity.Patron;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
  Patron findByEmail(String email);

}
