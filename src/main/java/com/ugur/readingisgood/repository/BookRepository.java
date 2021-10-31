package com.ugur.readingisgood.repository;

import com.ugur.readingisgood.enums.BookStatus;
import com.ugur.readingisgood.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByIdIsIn(List<String> bookIds);

}