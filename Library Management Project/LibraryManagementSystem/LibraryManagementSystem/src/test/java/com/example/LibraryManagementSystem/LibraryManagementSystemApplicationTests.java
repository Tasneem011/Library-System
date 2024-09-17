package com.example.LibraryManagementSystem;

import com.example.LibraryManagementSystem.Service.PatronService;
import com.example.LibraryManagementSystem.entity.Patron;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@RequiredArgsConstructor
class LibraryManagementSystemApplicationTests {

	PatronService  patronService;
	@Test
	void contextLoads() {}

}
