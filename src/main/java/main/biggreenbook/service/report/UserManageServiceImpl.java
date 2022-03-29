package main.biggreenbook.service.report;

import main.biggreenbook.entity.dao.report.UserManageMapper;
import main.biggreenbook.entity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserManageServiceImpl implements UserManageService{
    @Autowired
    private UserManageMapper userManageMapper;

    public void setUserMapper(UserManageMapper userManageMapper) {
        this.userManageMapper = userManageMapper;
    }

    @Override
    public User queryUserById(String uid) {
        return userManageMapper.queryUserById(uid);
    }

    @Override
    public void updateUser(Map<?, ?> map) {
        userManageMapper.updateUser(map);
    }

    @Override
    public List<User> queryAllUser() {
        return userManageMapper.queryAllUser();
    }

    @Override
    public List<User> getUsers(Map<?, ?> map) {
        return userManageMapper.getUsers(map);
    }

    @Override
    public int countAllUsers() {
        return userManageMapper.countAllUsers();
    }
}
