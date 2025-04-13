package com.company.librarymanagement.book.control.repository;

import com.company.librarymanagement.book.entity.Book;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> Strings.isEmpty(title) ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> Strings.isEmpty(author) ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase()+ "%");
    }

    public static Specification<Book> hasGenre(String genre) {
        return (root, query, cb) -> Strings.isEmpty(genre) ? null : cb.like(cb.lower(root.get("genre")),"%" + genre.toLowerCase() + "%");
    }
}
