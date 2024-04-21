package com.example.controller;

import com.example.model.Book;
import com.example.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BookController {

    private final BookService bService;

    public BookController(BookService bService){
        this.bService = bService;
    }


    @PostMapping("/addBook")
    public ResponseEntity<Book> AddBook(@RequestBody Book b){
        return new ResponseEntity<>(bService.AddBook(b), HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bService.getAllBooks(),HttpStatus.OK);
    }

    @PutMapping("/updateData/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book b, @PathVariable long id){
        return new ResponseEntity<>(bService.updateBook(b, id), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id){
        return new ResponseEntity<>(bService.deleteBook(id), HttpStatus.OK);
    }
}
