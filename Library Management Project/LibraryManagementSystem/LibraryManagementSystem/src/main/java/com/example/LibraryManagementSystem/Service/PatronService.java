package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.Repository.PatronRepository;
import com.example.LibraryManagementSystem.dto.PatronDto;
import com.example.LibraryManagementSystem.entity.Patron;
import com.example.LibraryManagementSystem.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j

public class PatronService {

    private final PatronRepository patronRepository;


    @Cacheable("patrons")
    public List<PatronDto> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "patrons", key = "#id")
    public PatronDto getPatronById(Long id)  {
        return patronRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @CachePut(value = "patrons", key = "#result.id")
    public PatronDto addPatron(PatronDto patronDto) {
        Patron patron = convertToEntity(patronDto);
        Patron savedPatron = patronRepository.save(patron);
        return convertToDto(savedPatron);
    }

    @Transactional
    @CachePut(value = "patrons", key = "#id")
    public PatronDto updatePatron(Long id,PatronDto patronDto) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron  not found with ID: " + id));

      patron.setName(patronDto.getName());
      patron.setEmail(patronDto.getEmail());

      Patron updatedPatron = patronRepository.save(patron);
      return convertToDto(updatedPatron);
    }

    @Transactional
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patron  not found with ID: " + id);
        }
        patronRepository.deleteById(id);
    }


    private PatronDto convertToDto(Patron patron) {
        PatronDto patronDto = new PatronDto();
        patronDto.setId(patron.getId());
       patronDto.setName(patron.getName());
       patronDto.setEmail(patron.getEmail());
        return patronDto;
    }

    private Patron convertToEntity(PatronDto patronDto) {
        Patron patron = new Patron();
        patron.setId(patronDto.getId());
        patron.setName(patronDto.getName());

        patron.setEmail(patronDto.getEmail());
        return patron;
    }

    public Patron getAuthenticatedPatron() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return  patronRepository.findByEmail(email);
    }
}