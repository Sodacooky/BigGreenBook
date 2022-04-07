package main.biggreenbook.service;

import main.biggreenbook.entity.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserManageService {
    User queryUserById(String uid);

    void updateUser(Map<?, ?> map);

    List<User> queryAllUser();

    List<User> getUsers(Map<?, ?> map);

    int countAllUsers();

}
