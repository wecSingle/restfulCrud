package com.arch.ssmrestcrud.dao.impl;

import com.arch.ssmrestcrud.dao.IUserDao;
import com.arch.ssmrestcrud.model.UserInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("userDaoImpl")
public class UserDaoImpl implements IUserDao {

    @Resource(name = "dbSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int countUserName(UserInfo mUser) {
        return sqlSessionTemplate.selectOne("UserMapper.countUserName", mUser);
    }

    @Override
    public boolean addUser(UserInfo mUser) {
        int num = sqlSessionTemplate.insert("UserMapper.addUser", mUser);
        boolean result = false;
        if (num > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public UserInfo getUserByName(String userName) {
        UserInfo mUser = sqlSessionTemplate.selectOne("UserMapper.getUserByName", userName);
        return mUser;
    }

    @Override
    public UserInfo getUserById(String id) {
        UserInfo mUser = sqlSessionTemplate.selectOne("UserMapper.getUserById", id);
        return mUser;
    }

    @Override
    public boolean updateUser(UserInfo mUser) {
        int num = sqlSessionTemplate.update("UserMapper.updateUser", mUser);
        boolean result = false;
        if (num > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteUser(String id){
        int num = sqlSessionTemplate.update("UserMapper.deleteUser", id);
        boolean result = false;
        if (num > 0) {
            result = true;
        }
        return result;
    }
}
