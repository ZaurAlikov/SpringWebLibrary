package ru.zauralikov.springlibrary.dao.interfaces;

import ru.zauralikov.springlibrary.entities.Book;

public interface EditBook {

    boolean save(Book book);
    boolean delete(Book book);
    boolean edit(Book book);
    boolean add(Book book);

}
