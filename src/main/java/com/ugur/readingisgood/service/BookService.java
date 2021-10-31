package com.ugur.readingisgood.service;

import com.ugur.readingisgood.dto.BookRequestDto;
import com.ugur.readingisgood.dto.BookResponseDto;
import com.ugur.readingisgood.enums.BookStatus;
import com.ugur.readingisgood.mapper.BookMapper;
import com.ugur.readingisgood.model.Book;
import com.ugur.readingisgood.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksById(List<String> bookIds) {
        return bookRepository.findByIdIsIn(bookIds);
    }

    public BookResponseDto create(BookRequestDto bookRequestDto) {
        Book book = bookMapper.mapToModel(bookRequestDto);
        bookRepository.save(book);
        return bookMapper.mapToResponse(book);
    }

    public BookResponseDto update(String id, BookRequestDto bookRequestDto) {
        Optional<Book> existingBook = bookRepository.findById(id);
        return existingBook.map(mapRequestToExistingModel(bookRequestDto))
                .map(bookRepository::save)
                .map(bookMapper::mapToResponse)
                .orElse(null);
    }

    private Function<Book, Book> mapRequestToExistingModel(BookRequestDto bookRequestDto) {
        return existing -> {
            bookMapper.mapToModelForUpdate(existing, bookRequestDto);
            return existing;
        };
    }

    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    public void setBooksStatus(List<Book> allOrderBooks, BookStatus bookStatus) {
        allOrderBooks.forEach(book -> {
            book.setStatus(bookStatus);
            bookRepository.save(book);
        });
    }
}
