package ru.zauralikov.springlibrary.dao.interfaces;

import org.springframework.security.core.userdetails.User;

public interface SecureLibrary {

    boolean login(User user);
    void logaut(User user);

}
