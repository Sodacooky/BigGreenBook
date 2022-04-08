package main.biggreenbook.service;

import main.biggreenbook.entity.dao.UserManageMapper;
import main.biggreenbook.entity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerPageService {
    @Autowired
    private UserManageMapper userManageMapper;

    public void setUserMapper(UserManageMapper userManageMapper) {
        this.userManageMapper = userManageMapper;
    }


    public User queryUserById(String uid) {
        return userManageMapper.queryUserById(uid);
    }


    public void updateUser(Map<?, ?> map) {
        userManageMapper.updateUser(map);
    }


    public List<User> queryAllUser() {
        return userManageMapper.queryAllUser();
    }


    public List<User> getUsers(Map<?, ?> map) {
        return userManageMapper.getUsers(map);
    }

    public int countAllUsers() {
        return userManageMapper.countAllUsers();
    }
}
