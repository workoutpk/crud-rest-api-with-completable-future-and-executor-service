package com.pk.app.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pk.app.model.Book;
import com.pk.app.repository.BookRepository;
import com.pk.app.response.ResponseHandler;

@RestController
@RequestMapping("api/v2")
public class BookController {
	@Autowired
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public CompletableFuture<Object> getAllBooks() {
        return CompletableFuture.supplyAsync(() -> bookRepository.findAll())
                .thenApply(books ->  ResponseHandler.generateResponse("sucess", HttpStatus.OK, books));
    }

    @GetMapping("/books/{id}")
    public CompletableFuture<Object> getBookById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> bookRepository.findById(id))
                .thenApply(book -> {
                    if (book == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    } else {
                        return new ResponseEntity<>(book, HttpStatus.OK);
                    }
                });
    }

    @PostMapping("/books")
    public CompletableFuture<ResponseEntity<Book>> saveBook(@RequestBody Book book) {
        return CompletableFuture.supplyAsync(() -> bookRepository.save(book))
                .thenApply(books -> new ResponseEntity<>(books, HttpStatus.CREATED));
    }

    @PutMapping("/books/{id}")
    public CompletableFuture<ResponseEntity<Book>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return CompletableFuture.supplyAsync(() -> bookRepository.save(book))
                .thenApply(books -> {
                    if (books == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    } else {
                        return new ResponseEntity<>(books, HttpStatus.OK);
                    }
                });
    }

//    @DeleteMapping("/books/{id}")
//    public CompletableFuture<ResponseEntity<Void>> deleteBook(@PathVariable Long id) {
//        return CompletableFuture.supplyAsync(() -> bookRepository.deleteById(id))
//                .thenApply(result -> {
//                    if (result == 0) {
//                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                    } else {
//                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                    }
//                });
//    }

}

