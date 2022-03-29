package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.ManageUserPage;
import main.biggreenbook.service.ManagerPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class ManagerPageController {
    @Autowired
    private ManagerPageService managerPageService;

    @GetMapping(value = "/query/{uid}")
    public User queryUserById(@PathVariable String uid) {

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/reset/{uid}")
    public User resetUserAvatar(@PathVariable String uid) {
        User user = managerPageService.queryUserById(uid);
        String avatar_path = "http://localhost:8080/static/avatar/default.png";
        Map<String, String> map = new HashMap<>();
        map.put("uid", user.getUid());
        map.put("avatar_path", user.getAvatarPath());
        managerPageService.updateUser(map);

        return user;
    }

    @GetMapping(value = "/allUser")
    public List<User> queryAllUser() {
        return managerPageService.queryAllUser();
    }

    @GetMapping(value = "/updateDesc/{uid}/{value}")
    public User updateDescription(@PathVariable String uid, @PathVariable String value) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("description", value);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/updateName/{uid}/{value}")
    public User updateNickName(@PathVariable String uid, @PathVariable String value) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("nickname", value);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/suspend/{uid}")
    public User suspendUser(@PathVariable String uid) {
        int state = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/restore/{uid}")
    public User restoreUser(@PathVariable String uid) {
        int state = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    // 实现分页功能
    @GetMapping(value = "/getUsers/{pageIndex}")
    public ManageUserPage getUsers(@PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("index", index);
        map.put("target", target);
        List<User> list = managerPageService.getUsers(map);
        int totalUsers = managerPageService.countAllUsers();

        return new ManageUserPage(list, totalUsers);
    }
}
