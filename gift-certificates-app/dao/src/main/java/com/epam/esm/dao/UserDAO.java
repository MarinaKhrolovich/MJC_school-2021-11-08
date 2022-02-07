package com.epam.esm.dao;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User add(User user);

    User get(int id);

    Optional<User> get(String username);

    List<User> get(Page page);

}
