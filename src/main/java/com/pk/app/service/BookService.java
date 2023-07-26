package com.pk.app.service;

import com.pk.app.model.Book;
import com.pk.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
        @Autowired
        private BookRepository bookRepository;

        private ExecutorService executorService = Executors.newFixedThreadPool(10);

        private final ReentrantLock lock = new ReentrantLock();

        private ResponseEntity<List<Book>> list;

        @Async
        public CompletableFuture<Book> saveBook(Book book) {
                lock.lock();
                try {
                        bookRepository.save(book);
                        return CompletableFuture.completedFuture(book);
                } finally {

                        lock.unlock();
                }
        }

        @Async
        public CompletableFuture<Book> findBookById(Long id) {
                lock.lock();
                try {

                        return CompletableFuture.supplyAsync(() -> {
                                return bookRepository.findById(id).orElse(null);
                        });


                } finally {
                        lock.unlock();
                }

        }

        @Async
        public CompletableFuture<Book> findById(Long id) {
                lock.lock();
                try {

                        return CompletableFuture.supplyAsync(() -> {
                                return bookRepository.findById(id).orElse(null);
                        });


                } finally {
                        lock.unlock();
                }

        }

        @Async
        public CompletableFuture<Book> findBookByName(Long id) {
                lock.lock();
                CompletableFuture<Book> completableFuture = new CompletableFuture<>();
                executorService.submit(() -> {
                        try {

                                return CompletableFuture.supplyAsync(() -> {
                                        return bookRepository.findById(id).orElse(null);
                                });


                        } finally {
                                lock.unlock();
                        }
                });

                return completableFuture;

        }

        public CompletableFuture<Book> getBook(String isbn) {
                CompletableFuture<Book> future = CompletableFuture.supplyAsync(() -> {
                        // Do some work here, e.g. call a database or make an API call.
                        Book book = new Book();
                        //book.setIsbn(isbn);
                        //book.setTitle("The Lord of the Rings");
                        //book.setAuthor("J.R.R. Tolkien");
                        // book.setName("pk");
                        return book;
                }, executorService);

                // Handle any exceptions that occur while getting the book.
                future.exceptionally(ex -> {
                        System.out.println("Error getting book: " + ex.getMessage());
                        return null;
                });

                return future;
        }
        @Async
        public List<Book> getAllBook() {

                return new ArrayList<Book>(bookRepository.findAll());
        }

        @Async
        public CompletableFuture<List<Book>> getBookByCompletableFuture() {

                return CompletableFuture.supplyAsync(() -> bookRepository.findAll());

        }

        @Async
        public CompletableFuture<List<Book>> findAllBooks() {
                return CompletableFuture.supplyAsync(() -> StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList()));
        }

        @Async
        public CompletableFuture<Void> deleteBook(Long id) {
                return CompletableFuture.runAsync(() -> bookRepository.deleteById(id));
        }

        @Async
        public CompletableFuture<Book> updateBook(Long id, Book book) {
                return CompletableFuture.supplyAsync(() -> {
                        Optional<Book> optionalBook = bookRepository.findById(id);
                        if (optionalBook.isPresent()) {
                                Book existingBook = optionalBook.get();

                                existingBook.setName(book.getName());
                                return bookRepository.save(existingBook);
                        }
                        return null;
                });
        }

        public void close() {
                executorService.shutdown();
                try {
                        executorService.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                        System.out.println("Error closing executor service: " + e.getMessage());
                }
        }


}
