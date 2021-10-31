package com.ugur.readingisgood.mapper;

import com.ugur.readingisgood.dto.BookRequestDto;
import com.ugur.readingisgood.dto.BookResponseDto;
import com.ugur.readingisgood.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "status", constant = "AVAILABLE")
    public Book mapToModel(BookRequestDto bookRequestDto);

    public void mapToModelForUpdate(@MappingTarget Book book, BookRequestDto bookRequestDto);

    BookResponseDto mapToResponse(Book book);
}
