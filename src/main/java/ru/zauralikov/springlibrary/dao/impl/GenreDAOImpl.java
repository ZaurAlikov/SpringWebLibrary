package ru.zauralikov.springlibrary.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zauralikov.springlibrary.dao.interfaces.GenreDAO;
import ru.zauralikov.springlibrary.entities.Genre;

import java.util.List;

@Transactional(readOnly = true)
@Repository("genreDAO")
public class GenreDAOImpl implements GenreDAO {

    private SessionFactory sessionFactory;

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = sessionFactory.getCurrentSession()
                .createQuery("select g from Genre g order by name", Genre.class).list();
        Genre allBooks = new Genre();
        allBooks.setName("Все книги");
        genres.add(0, allBooks);
        return genres;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
}
