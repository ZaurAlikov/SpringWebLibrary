package ru.zauralikov.springlibrary.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zauralikov.springlibrary.dao.interfaces.BookSearch;
import ru.zauralikov.springlibrary.entities.Author;
import ru.zauralikov.springlibrary.entities.Book;
import ru.zauralikov.springlibrary.entities.Genre;

import javax.annotation.Resource;
import java.util.List;

@Transactional(readOnly = true)
@Repository("bookSearch")
public class BookSearchImpl implements BookSearch {

    private SessionFactory sessionFactory;
    private ProjectionList bookProjection;

    public BookSearchImpl(){
        bookProjection = Projections.projectionList();
        bookProjection.add(Projections.property("id"), "id");
        bookProjection.add(Projections.property("name"), "name");
        bookProjection.add(Projections.property("pageCount"), "pageCount");
        bookProjection.add(Projections.property("isbn"), "isbn");
        bookProjection.add(Projections.property("genre"), "genre");
        bookProjection.add(Projections.property("author"), "author");
        bookProjection.add(Projections.property("publishYear"), "publishYear");
        bookProjection.add(Projections.property("publisher"), "publisher");
        bookProjection.add(Projections.property("image"), "image");
        bookProjection.add(Projections.property("descr"), "descr");
        bookProjection.add(Projections.property("rating"), "rating");
        bookProjection.add(Projections.property("voteCount"), "voteCount");
    }


    @Override
    public List<Book> getBooks() {
        List<Book> books =  createBookList(createBookCriteria());
        return books;
    }

    @Override
    public List<Book> getBooks(Author author) {
        List<Book> books =  createBookList(createBookCriteria()
                .add(Restrictions.ilike("author.fio", author.getFio(), MatchMode.ANYWHERE)));
        return books;
    }

    @Override
    public List<Book> getBooks(String bookName) {
        List<Book> books =  createBookList(createBookCriteria()
                .add(Restrictions.ilike("b.name", bookName, MatchMode.ANYWHERE)));
        return books;
    }

    @Override
    public List<Book> getBooks(Genre genre) {
        List<Book> books =  createBookList(createBookCriteria()
                .add(Restrictions.ilike("genre.name", genre.getName(), MatchMode.ANYWHERE)));
        return books;
    }

//    @Override
//    public List<Book> getBooks(Character letter) {
//        List<Book> books =  createBookList(createBookCriteria()
//                .add(Restrictions.ilike("b.name", letter.toString(), MatchMode.START)));
//        return books;
//    }

    @Resource(name="sessionFactory")
    void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private DetachedCriteria createBookCriteria(){
        DetachedCriteria bookListCriteria = DetachedCriteria.forClass(Book.class, "b");
        createAliases(bookListCriteria);
        return bookListCriteria;
    }

    private void createAliases(DetachedCriteria criteria){
        criteria.createAlias("b.author", "author");
        criteria.createAlias("b.genre", "genre");
        criteria.createAlias("b.publisher", "publisher");
    }

    @SuppressWarnings("unchecked")
    private List<Book> createBookList(DetachedCriteria bookListCriteria){
        Criteria criteria = bookListCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
        criteria.addOrder(Order.asc("b.name"))
                .setProjection(bookProjection)
                .setResultTransformer(Transformers.aliasToBean(Book.class));
        return criteria.list();
    }
}
