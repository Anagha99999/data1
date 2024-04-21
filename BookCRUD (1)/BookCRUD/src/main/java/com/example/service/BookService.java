package com.example.service;

import com.example.model.Book;
import com.example.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private final BookRepo bRepo;

    public BookService(BookRepo bRepo) {
        this.bRepo = bRepo;
    }

    public List<Book> getAllProducts() {
        return bRepo.findAll();
    }

    public List<Book> getAllBooks() {
        return bRepo.findAll();
    }


    public Book getBookById(long id) {
        Optional<Book> book = bRepo.findById(id);
        return book.orElse(null);
    }

    public Book AddBook(Book B) {
        return bRepo.save(B);

    }

    public String deleteBook(long id) {
        bRepo.deleteById(id);
        return "Book Deleted";
    }


    public Book updateBook(Book b, long id) {
        Optional<Book> book1 = bRepo.findById(b.getId());
        if(book1.isPresent()) {
            Book tempBook = book1.get();
            tempBook.setTitle(b.getTitle());
            tempBook.setAuthor(b.getAuthor());
            return bRepo.save(tempBook);
        }
        return null;
    }

}
