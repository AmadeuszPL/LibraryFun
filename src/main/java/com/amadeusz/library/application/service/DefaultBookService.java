package com.amadeusz.library.application.service;

import com.amadeusz.library.application.exceptions.CustomerNotFoundException;
import com.amadeusz.library.application.exceptions.NoBookInRepositoryException;
import com.amadeusz.library.infrastructure.repository.entities.BookEntity;
import com.amadeusz.library.infrastructure.repository.entities.mappers.BookEntityMapper;
import com.amadeusz.library.infrastructure.repository.entities.mappers.DefaultBookEntityMapper;
import com.amadeusz.library.infrastructure.repository.BookJpaRepository;
import com.amadeusz.library.application.model.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("bookService")
public class DefaultBookService implements BookService {

    @Autowired
    private BookJpaRepository bookRepository;

    private final BookEntityMapper mapper = new DefaultBookEntityMapper();

    @Override
    public Book add(Book book) {
        BookEntity bookEntity = mapper.map(book);
        BookEntity bookEntity1 = bookRepository.saveAndFlush(bookEntity);
        return mapper.map(bookEntity1);
    }

    @Override
    public BookEntity getByISBN(String isbn) {
        Optional<BookEntity> byId
                = bookRepository.findById(isbn.replaceAll("[^0-9]", ""));
        if (byId.isEmpty()) {
            throw new NoBookInRepositoryException("Book of this ISBN is not in repository");
        }
        return byId.get();
    }

    @Override
    public Book updateBook(String isbn, Book book) {
        Optional<BookEntity> bookEntity
                = bookRepository.findById(isbn.replaceAll("[^0-9]", ""));
        if (bookEntity.isEmpty()) {
            throw new CustomerNotFoundException("Update failed, customer not found");
        } else {
            BookEntity patchedBook = mapper.map(book);
            bookRepository.saveAndFlush(patchedBook);
            return book;
        }
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);
        return bookEntities.map(mapper::map);
    }

    @Override
    public Page<Book> searchByYear(int publicationYear, Pageable pageable) {
        Page<BookEntity> bookEntities
                = bookRepository.findByPublicationYear(publicationYear, pageable);
        return bookEntities.map(mapper::map);
    }

    @Override
    public Page<Book> searchByAuthorName(String authorName, Pageable pageable) {
        Page<BookEntity> bookEntities
                = bookRepository.findByAuthorName(authorName, pageable);
        return bookEntities.map(mapper::map);
    }

    @Override
    public Page<Book> searchByCategory(String category, Pageable pageable) {
        Page<BookEntity> bookEntities
                = bookRepository.findByCategory(category.toUpperCase(), pageable);
        return bookEntities.map(mapper::map);
    }

    @Override
    public Page<Book> searchByTitle(String title, Pageable pageable) {
        Page<BookEntity> bookEntities
                = bookRepository.findByTitleLike(title, pageable);
        return bookEntities.map(mapper::map);
    }

    @Override
    @Transactional
    public void removeByISBN(String isbn) {
        bookRepository.deleteByIsbn(isbn.replaceAll("[^0-9]", ""));
    }

    @Override
    public String toString() {
        return "BookService{" + "bookRepository=" + bookRepository + '}';
    }

}
