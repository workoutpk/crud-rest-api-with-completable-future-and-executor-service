package com.pk.app.controller;

import com.pk.app.model.Book;
import com.pk.app.repository.BookRepository;
import com.pk.app.response.ResponseHandler;
import com.pk.app.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(
        path = "/api/books",
        consumes = {"*/*"},
//	    consumes    = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = MediaType.APPLICATION_JSON_VALUE,
        method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class BooksController {

        @Autowired
        private BookService bookService;
        private final BookRepository bookRepository;

        public BooksController(BookRepository bookRepository) {
                this.bookRepository = bookRepository;
        }

        ;

        @GetMapping("/get_hello")
        public static CompletableFuture<ResponseEntity<Object>> getHello() {
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                        // Do some work here, e.g. call a database or make an API call.
                        try {
                                // Do some work here, e.g. call a database or make an API call.
                                return "Hello World!";
                        } catch (Exception e) {
                                // Handle the exception here.
                                return null;
                        }
                });
                return future.thenApply(result -> {
                        if (result == null) {
                                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        } else {
                                return ResponseHandler.generateResponse("Successfully retrieved data", HttpStatus.OK, result);
                        }
                });
        }

        @PostMapping("/book_add")
        public CompletableFuture<Object> saveBook(@RequestBody @Valid Book book) {
                Book book2 = new Book();
                book2.setName("U2");
                CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
                        try {

                                return bookRepository.save(book);
                        } catch (Exception e) {
                                // TODO: handle exception
                                return null;
                        }

                });
                return future.thenApply(result -> {
                        if (result == null) {
                                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        } else {
                                return ResponseHandler.generateResponse("Successfully retrieved data", HttpStatus.OK, result);
                        }
                });

        }

        @PostMapping("book_add_with_response")
        public CompletableFuture<ResponseEntity<Book>> saveBookResponse(@RequestBody @Valid Book book) {
                return CompletableFuture.supplyAsync(() -> bookRepository.save(book))
                        .thenApply(books -> new ResponseEntity<>(books, HttpStatus.CREATED));
        }

        @GetMapping("/{id}")
        public CompletableFuture<Book> findBookById(@PathVariable Long id) {

                return bookService.findBookById(id);
        }

        @PatchMapping("/book_update/{id}")
        public CompletableFuture<Book> bookUpdateById(@PathVariable Long id, @RequestBody Book book) {
                System.out.println("body data::  " + book.toString());
                return bookService.updateBook(id, book);
        }

        @GetMapping("/get_all_book")
        public ResponseEntity<Object> getbookList() {
                try {
                        ArrayList<Book> result = (ArrayList<Book>) bookService.getAllBook();
                        return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);


                } catch (Exception e) {
                        // TODO: handle exception
                        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);


                }

        }


        @GetMapping("/get_book_by_completable_future")
        public CompletableFuture<Object> getBookByCompletableFuture() {
                CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
                        try {
                                System.out.println("list of data ::: " + bookService.getBookByCompletableFuture());
                                return bookService.getAllBook();
                        } catch (Exception e) {
                                // TODO: handle exception
                                return null;
                        }

                });
                return future.thenApply(result -> {
                        if (result == null) {
                                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        } else {
                                return ResponseHandler.generateResponse("Successfully retrieved data", HttpStatus.OK, result);
                        }
                });

        }

        @GetMapping("/book/{isbn}")
        public CompletableFuture<Book> book(@PathVariable String isbn) {
                return null;
        }

}
