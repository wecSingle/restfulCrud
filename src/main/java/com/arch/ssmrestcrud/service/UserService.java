package com.arch.ssmrestcrud.service;

import com.arch.ssmrestcrud.dao.IUserDao;
import com.arch.ssmrestcrud.model.UserInfo;
import com.arch.ssmrestcrud.service.base.BaseService;
import com.arch.ssmrestcrud.util.BgyResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserService extends BaseService {

    @Resource(name = "userDaoImpl")
    private IUserDao iUserDao;


    /**
     * 添加用户
     *
     * @param UserInfoJson
     * @return
     */
    public String addUser(String UserInfoJson) {
        BgyResult br = new BgyResult();
        UserInfo UserInfo = json.parseObject(UserInfoJson, UserInfo.class);
        int count = iUserDao.countUserName(UserInfo);
        if (count > 0) {
            br.setCode("400");
            br.setMsg("用户名已存在");
            return json.toJSONString(br);
        }
        UserInfo.setId(get32UUID());
        boolean result = iUserDao.addUser(UserInfo);
        if (result) {
            br.setCode("200");
            br.setMsg("注册成功");
            br.setData(UserInfo);
        } else {
            br.setCode("400");
            br.setMsg("注册失败");
        }
        return json.toJSONString(br);
    }

    /**
     * 通过用户名获取用户
     *
     * @param userName
     * @return
     */
    public String getUserByName(String userName) {
        BgyResult br = new BgyResult();
        UserInfo UserInfo = iUserDao.getUserByName(userName);
        br.setCode("200");
        br.setMsg("Ok");
        br.setData(UserInfo);
        return json.toJSONString(br);
    }

    /**
     * 编辑用户
     *
     * @param UserInfoJson
     * @return
     */
    public String updateUser(String id, String UserInfoJson) {
        BgyResult br = new BgyResult();
        UserInfo UserInfo = json.parseObject(UserInfoJson, UserInfo.class);
        UserInfo myUserInfo = iUserDao.getUserById(id);
        if (myUserInfo == null) {
            br.setCode("400");
            br.setMsg("用户不存在");
            return json.toJSONString(br);
        }
        boolean result = iUserDao.updateUser(UserInfo);
        if (result) {
            br.setCode("200");
            br.setMsg("修改成功");
        } else {
            br.setCode("400");
            br.setMsg("修改失败");
        }
        return json.toJSONString(br);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public String deleteUser(String id) {
        BgyResult br = new BgyResult();
        UserInfo myUserInfo = iUserDao.getUserById(id);
        if (myUserInfo == null) {
            br.setCode("400");
            br.setMsg("用户不存在");
            return json.toJSONString(br);
        }
        boolean result = iUserDao.deleteUser(id);
        if (result) {
            br.setCode("200");
            br.setMsg("删除成功");
        } else {
            br.setCode("400");
            br.setMsg("删除失败");
        }
        return json.toJSONString(br);
    }
}
