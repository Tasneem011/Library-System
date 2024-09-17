package com.example.LibraryManagementSystem.Controller;


import com.example.LibraryManagementSystem.Service.PatronService;
import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.dto.PatronDto;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
@AllArgsConstructor
@Validated
public class PatronController {

    private final PatronService patronService;



    // Retrieve a list of all patrons
    @GetMapping("/all")
    public ResponseEntity<List<PatronDto>> getAllPatrons() {
        List<PatronDto> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(patrons);
    }

    // Retrieve details of a specific patron by ID
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable Long id) {
        PatronDto patronDto = patronService.getPatronById(id);
        return ResponseEntity.ok(patronDto);
    }

    // Add a new patron to the system
    @PostMapping("/add")
    public ResponseEntity<PatronDto> addPatron(@RequestBody @Valid PatronDto patronDTO) {
        PatronDto createdPatron = patronService.addPatron(patronDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatron);
    }


    // Update an existing patron's information
    @PutMapping("/{id}")
    public ResponseEntity<PatronDto> updatePatron(@PathVariable Long id, @RequestBody @Valid PatronDto patronDTO)  {
        PatronDto updatedPatron = patronService.updatePatron(id, patronDTO);
        return ResponseEntity.ok(updatedPatron);
    }

    // Remove a patron from the system
    @DeleteMapping("/{id}")
    public ResponseEntity<PatronDto> deletePatron(@PathVariable Long id)  {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }

}
