package ru.zauralikov.springlibrary.dao.interfaces;

import ru.zauralikov.springlibrary.entities.Book;

public interface ShowBook {

    void showBook(Book book);
    void downloadBook(Book book);
    void voteBook(Book book);

}
