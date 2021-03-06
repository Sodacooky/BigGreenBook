package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserManageMapper {
    public User queryUserById(String uid);

    public void updateUser(Map<?, ?> map);

    public List<User> queryAllUser();

    public List<User> getUsers(Map<?, ?> map);

    public int countAllUsers();

    public int countQueryUser(Map<?, ?> map);
}
