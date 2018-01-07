package ru.zauralikov.springlibrary.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zauralikov.springlibrary.dao.interfaces.EditBook;
import ru.zauralikov.springlibrary.entities.Book;

import javax.annotation.Resource;

@Transactional
@Repository("editBook")
public class EditBookImpl implements EditBook {

    private SessionFactory sessionFactory;

    @Override
    public boolean save(Book book) {
        return false;
    }

    @Override
    public boolean delete(Book book) {
        int result = sessionFactory.getCurrentSession().createQuery("delete Book b where b.id = :id")
                .setParameter("id", book.getId()).executeUpdate();
        return result > 0;
    }

    @Override
    public boolean edit(Book book) {
        return false;
    }

    @Override
    public boolean add(Book book) {
        return false;
    }

    @Resource(name="sessionFactory")
    void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

}
