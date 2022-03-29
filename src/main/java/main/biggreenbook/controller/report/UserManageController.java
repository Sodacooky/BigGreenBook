package main.biggreenbook.controller.report;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.ManageUserPage;
import main.biggreenbook.service.report.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserManageController {
    @Autowired
    private UserManageService userManageService;

    @GetMapping(value = "/query/{uid}")
    public User queryUserById(@PathVariable String uid) {

        return userManageService.queryUserById(uid);
    }

    @GetMapping(value = "/reset/{uid}")
    public User resetUserAvatar(@PathVariable String uid) {
        User user = userManageService.queryUserById(uid);
        String avatar_path = "http://localhost:8080/static/avatar/default.png";
        Map<String, String> map = new HashMap<>();
        map.put("uid", user.getUid());
        map.put("avatar_path", avatar_path);
        userManageService.updateUser(map);

        return user;
    }

    @GetMapping(value = "/allUser")
    public List<User> queryAllUser() {
        return userManageService.queryAllUser();
    }

    @GetMapping(value = "/updateDesc/{uid}/{value}")
    public User updateDescription(@PathVariable String uid, @PathVariable String value) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("description", value);
        userManageService.updateUser(map);

        return userManageService.queryUserById(uid);
    }

    @GetMapping(value = "/updateName/{uid}/{value}")
    public User updateNickName(@PathVariable String uid, @PathVariable String value) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("nickname", value);
        userManageService.updateUser(map);

        return userManageService.queryUserById(uid);
    }

    @GetMapping(value = "/suspend/{uid}")
    public User suspendUser(@PathVariable String uid) {
        int state = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        userManageService.updateUser(map);

        return userManageService.queryUserById(uid);
    }

    @GetMapping(value = "/restore/{uid}")
    public User restoreUser(@PathVariable String uid) {
        int state = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        userManageService.updateUser(map);

        return userManageService.queryUserById(uid);
    }

    // 实现分页功能
    @GetMapping(value = "/getUsers/{pageIndex}")
    public ManageUserPage getUsers(@PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        }
        else {
            index = (pageIndex-1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("index", index);
        map.put("target", target);
        List<User> list = userManageService.getUsers(map);
        int totalUsers = userManageService.countAllUsers();

        return new ManageUserPage(list, totalUsers);
    }
}
