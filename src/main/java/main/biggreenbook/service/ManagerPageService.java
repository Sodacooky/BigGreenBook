package main.biggreenbook.service;

import main.biggreenbook.entity.dao.UserManageMapper;
import main.biggreenbook.entity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
<<<<<<< HEAD:src/main/java/main/biggreenbook/service/UserManageServiceImpl.java
public class UserManageServiceImpl implements UserManageService {
=======
public class ManagerPageService {
>>>>>>> 049cb7235c9e854795f8160766a57b1d3e0dc179:src/main/java/main/biggreenbook/service/ManagerPageService.java
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
