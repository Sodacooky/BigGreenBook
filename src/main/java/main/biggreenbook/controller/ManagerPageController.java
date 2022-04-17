package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.pojo.ReportMessage;
import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.ManageContentPage;
import main.biggreenbook.entity.vo.ManageReportPage;
import main.biggreenbook.entity.vo.ManageUserPage;
import main.biggreenbook.service.ContentManageService;
import main.biggreenbook.service.ManagerPageService;
import main.biggreenbook.service.ReportService;
import main.biggreenbook.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/manage")
public class ManagerPageController {
    @Autowired
    private ManagerPageService managerPageService;
    @Autowired
    private ContentManageService contentManageService;
    @Autowired
    private ReportService reportService;

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
        int state = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/restore/{uid}")
    public User restoreUser(@PathVariable String uid) {
        int state = 0;
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


    // 进入页面时初始加载的内容
    @GetMapping(value = "/getContents/{pageIndex}/{dateValue}")
    public ManageContentPage getContents(@PathVariable int pageIndex, @PathVariable Date[] dateValue) {
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
        map.put("start", dateValue[0]);
        map.put("end", dateValue[1]);
        List<ContentMessage> list = contentManageService.getContents(map);
        int totalContents = contentManageService.countAllContents(map);

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/deleteSelect/{select}")
    public int deleteSelect(@PathVariable List<Integer> select) {

        return contentManageService.deleteSelect(select);
    }

    @GetMapping(value = "/queryContents/{inputName}/{pageIndex}/{dateValue}")
    public ManageContentPage queryConTents(@PathVariable String inputName, @PathVariable int pageIndex, @PathVariable Date[] dateValue) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("title", inputName);
        map.put("index", index);
        map.put("target", target);
        map.put("start", dateValue[0]);
        map.put("end", dateValue[1]);
        List<ContentMessage> list = contentManageService.queryContents(map);
        int totalContents = contentManageService.countQueryContents(map);

        return new ManageContentPage(list, totalContents);
    }


    @GetMapping(value = "/queryUid/{uid}/{pageIndex}/{dateValue}")
    public ManageContentPage queryContentsByUid(@PathVariable String uid, @PathVariable int pageIndex, @PathVariable Date[] dateValue) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("index", index);
        map.put("target", target);
        map.put("start", dateValue[0]);
        map.put("end", dateValue[1]);
        List<ContentMessage> list = contentManageService.queryContents(map);
        int totalContents = contentManageService.countQueryContents(map);

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/queryNickname/{nickname}/{pageIndex}/{dateValue}")
    public ManageContentPage queryContentsByNickname(@PathVariable String nickname, @PathVariable int pageIndex, @PathVariable Date[] dateValue) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("index", index);
        map.put("target", target);
        map.put("start", dateValue[0]);
        map.put("end", dateValue[1]);
        List<ContentMessage> list = contentManageService.queryContents(map);
        int totalContents = contentManageService.countQueryContents(map);

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/check/{cid}")
    public ContentMessage checkContent(@PathVariable String cid) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);

        return contentManageService.checkContent(map);
    }

    @GetMapping(value = "/nextContent/{cid}/{date}")
    public ManageContentPage getNextContents(@PathVariable String cid, @PathVariable String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("end", date);
        System.out.println("------------------------------------------------------");
        System.out.println("next: " + contentManageService.getNextContents(map));
        System.out.println("------------------------------------------------------");

        return new ManageContentPage(contentManageService.getNextContents(map));
    }

    @GetMapping(value = "/previousContent/{cid}/{date}")
    public ManageContentPage getPreviousContents(@PathVariable String cid, @PathVariable String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("start", date);
        System.out.println("------------------------------------------------------");
        System.out.println("privious: " + contentManageService.getPreviousContents(map));
        System.out.println("------------------------------------------------------");

        return new ManageContentPage(contentManageService.getPreviousContents(map));
    }

    // 获得举报消息
    @GetMapping(value = "/getReports/{pageIndex}")
    public ManageReportPage getReports(@PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 12;
        }
        target = index + 12;
        Map<String, Integer> map = new HashMap<>();
        map.put("index", index);
        map.put("target", target);
        map.put("solved", 0);
        List<ReportMessage> list = reportService.getReports(map);
        int totalReports = reportService.countAllReports(0);

        return new ManageReportPage(list, totalReports);
    }

    // 忽略举报
    @GetMapping(value = "/ignore/{uid}/{cid}")
    public int handleReports(@PathVariable String uid, @PathVariable String cid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("cid", cid);

        return reportService.handleReports(map);
    }

    @GetMapping(value = "/getIgnore/{pageIndex}")
    public ManageReportPage getIgnoreReports(@PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 12;
        }
        target = index + 12;
        Map<String, Integer> map = new HashMap<>();
        map.put("index", index);
        map.put("target", target);
        map.put("solved", 1);
        List<ReportMessage> list = reportService.getReports(map);
        int totalReports = reportService.countAllReports(1);

        return new ManageReportPage(list, totalReports);
    }


    // 管理员登录
    @GetMapping(value = "/manageLogin/{username}/{password}")
    public Map<String, String> ManageLogin(@PathVariable String username, @PathVariable String password) {
        System.out.println("-----------------------------------------------------");
        System.out.println("已接收到请求！");
        System.out.println("-----------------------------------------------------");
        Map<String, String> map = new HashMap<>();
        String state = "认证失败！";
        if (username.equals("admin") && password.equals("12345")) {
            state = "认证通过";
            String token = JWTUtils.getToken(map);
            map.put("token", token);
            System.out.println(token);
            System.out.println("-----------------------------------------------------");
        }
        map.put("state", state);
        map.put("id", username);

        return map;
    }
}
