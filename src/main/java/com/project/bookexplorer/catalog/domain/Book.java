package com.project.bookexplorer.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private final Integer year;
}
