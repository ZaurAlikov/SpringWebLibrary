package ru.zauralikov.springlibrary.objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.zauralikov.springlibrary.entities.Genre;
import ru.zauralikov.springlibrary.enums.SearchType;

@Component
@Scope("singleton")
public class SearchCriteria {

    private String text;
    private SearchType searchType = SearchType.TITLE;
    private Genre genre;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
