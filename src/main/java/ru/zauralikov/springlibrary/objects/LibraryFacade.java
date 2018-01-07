package ru.zauralikov.springlibrary.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.zauralikov.springlibrary.dao.interfaces.BookSearch;
import ru.zauralikov.springlibrary.dao.interfaces.EditBook;
import ru.zauralikov.springlibrary.entities.Author;
import ru.zauralikov.springlibrary.entities.Book;
import ru.zauralikov.springlibrary.enums.SearchType;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("libraryFacade")
@Scope("singleton")
public class LibraryFacade {

    private static final Logger log = LoggerFactory.getLogger(LibraryFacade.class);

    private BookSearch bookSearch;
    private EditBook editBook;
    private SearchCriteria searchCriteria;
    private List<Book> books;

    @PostConstruct
    @SuppressWarnings("unchecked")
    void init(){
        log.error(this.toString());
        books = bookSearch.getBooks();
    }

    @SuppressWarnings("unchecked")
    private void getAllBooks() {
        books = bookSearch.getBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void searchByText(){
        if (searchCriteria.getSearchType() == SearchType.TITLE){
            books = bookSearch.getBooks(searchCriteria.getText());
        } else if (searchCriteria.getSearchType() == SearchType.AUTHOR){
            Author author = new Author();
            author.setFio(searchCriteria.getText());
            books = bookSearch.getBooks(author);
        }
    }

    @SuppressWarnings("unchecked")
    public void searchByGenre(){
        if (searchCriteria.getGenre().getName().equals("Все книги")){
            getAllBooks();
        } else {
            books = bookSearch.getBooks(searchCriteria.getGenre());
        }
        searchCriteria.setText("");
    }

    public byte[] getContent(long id){
        return bookSearch.getContent(id);
    }

    public void deleteBook(int index){
        boolean result = editBook.delete(books.get(index));
        if(result){
            books.remove(index);
        }
    }

    @Autowired
    public void setSearchCriteria(SearchCriteria searchCriteria){
        this.searchCriteria = searchCriteria;
    }

    @Autowired
    public void setBookSearch(BookSearch bookSearch){
        this.bookSearch = bookSearch;
    }

    @Autowired
    public void setEditBook(EditBook editBook){
        this.editBook = editBook;
    }
}
