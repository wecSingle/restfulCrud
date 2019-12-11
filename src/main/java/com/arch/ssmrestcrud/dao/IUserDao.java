package com.arch.ssmrestcrud.dao;

import com.arch.ssmrestcrud.model.UserInfo;

public interface IUserDao {

    int countUserName(UserInfo mUser);

    boolean addUser(UserInfo mUser);

    UserInfo getUserByName(String userName);

    UserInfo getUserById(String id);

    boolean updateUser(UserInfo mUser);

    boolean deleteUser(String id);
}