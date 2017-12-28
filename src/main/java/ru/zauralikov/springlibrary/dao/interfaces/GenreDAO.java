package ru.zauralikov.springlibrary.dao.interfaces;

import ru.zauralikov.springlibrary.entities.Genre;

import java.util.List;

public interface GenreDAO {

    List<Genre> getGenres();
}
