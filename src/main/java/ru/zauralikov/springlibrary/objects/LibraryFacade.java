package ru.zauralikov.springlibrary.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zauralikov.springlibrary.dao.interfaces.BookSearch;
import ru.zauralikov.springlibrary.entities.Book;

import java.util.List;

@Component
public class LibraryFacade {

    private BookSearch bookSearch;
    private List<Book> books;

    @Autowired
    @SuppressWarnings("unchecked")
    public void setBookSearch(BookSearch bookSearch){
        this.bookSearch = bookSearch;
        books = bookSearch.getBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

}
