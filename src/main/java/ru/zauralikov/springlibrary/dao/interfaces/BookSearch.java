package ru.zauralikov.springlibrary.dao.interfaces;

import ru.zauralikov.springlibrary.entities.Author;
import ru.zauralikov.springlibrary.entities.Book;
import ru.zauralikov.springlibrary.entities.Genre;

import java.util.List;

public interface BookSearch {

    List getBooks();
    List<Book> getBooks(Author author);
    List<Book> getBooks(String bookName);
    List<Book> getBooks(Genre genre);
    byte[] getContent(long id);
//    List<Book> getBooks(Character letter);

}
