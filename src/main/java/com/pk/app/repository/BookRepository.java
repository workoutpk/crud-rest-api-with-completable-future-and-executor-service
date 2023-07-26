package com.pk.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pk.app.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	@Query("from Book S WHERE S.name=?1")
    List<Book> findByName(String string);

}
