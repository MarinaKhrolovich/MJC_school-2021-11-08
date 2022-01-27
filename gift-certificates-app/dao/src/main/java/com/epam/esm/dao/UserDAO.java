package com.epam.esm.dao;

import com.epam.esm.bean.Page;
import com.epam.esm.bean.User;

import java.util.List;

public interface UserDAO {

    User add(User user);

    User get(int id);

    List<User> get(Page page);

}
