package ru.zauralikov.springlibrary.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.zauralikov.springlibrary.enums.SearchType;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;

@Component
public class Utils {

    private MessageSource msg;

    private Map<String, SearchType> searchTypeList = new HashMap<>();
    private SearchType selectedSearchType = SearchType.TITLE;

    public Map<String, SearchType> getSearchTypeList() {
        searchTypeList.clear();
        searchTypeList.put(msg.getMessage("author_name", null, FacesContext.getCurrentInstance().getViewRoot().getLocale()), SearchType.AUTHOR);
        searchTypeList.put(msg.getMessage("book_name", null, FacesContext.getCurrentInstance().getViewRoot().getLocale()), SearchType.TITLE);
        return searchTypeList;
    }

    public void setSearchTypeList(Map<String, SearchType> searchTypeList) {
        this.searchTypeList = searchTypeList;
    }

    public SearchType getSelectedSearchType() {
        return selectedSearchType;
    }

    @Autowired
    public void setMessageSource(MessageSource msg){
        this.msg = msg;
    }

}
