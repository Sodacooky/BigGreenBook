package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.pojo.ReportMessage;
import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.ManageContentPage;
import main.biggreenbook.entity.vo.ManageReportPage;
import main.biggreenbook.entity.vo.ManageUserPage;
import main.biggreenbook.service.ContentManageService;
import main.biggreenbook.service.ManagerPageService;
import main.biggreenbook.service.MessageService;
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
    @Autowired
    private MessageService messageService;

    /**
    * 通过uid查询用户
    * **/
    @GetMapping(value = "/query/{uid}")
    public User queryUserById(@PathVariable String uid) {
        return managerPageService.queryUserById(uid);
    }

    /**
     * 通过昵称查询用户
     *
     * @return**/
    @GetMapping(value = "/queryName/{pageIndex}/{input}")
    public ManageUserPage queryUserByName(@PathVariable int pageIndex, @PathVariable String input) {
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
        map.put("nickname", input);
        List<User> list = managerPageService.getUsers(map);
        int totalUsers = managerPageService.countQueryUser(map);

        return new ManageUserPage(list, totalUsers);
    }

    /**
     * 通过uid重置用户头像
     * @param uid 用户uid
     * @return user 返回用户
     * **/
    @GetMapping(value = "/reset/{uid}")
    public User resetUserAvatar(@PathVariable String uid) {
        User user = managerPageService.queryUserById(uid);
        String avatar_path = "avatar/default.jpg";
        Map<String, String> map = new HashMap<>();
        map.put("uid", user.getUid());
        map.put("avatar_path", avatar_path);
        managerPageService.updateUser(map);

        return user;
    }

    @GetMapping(value = "/allUser")
    public List<User> queryAllUser() {
        return managerPageService.queryAllUser();
    }


    /**
     * 修改用户签名
     * @param uid 用户uid
     * @param value 用户昵称
     * @return user 返回用户
     */
    @GetMapping(value = "/updateDesc/{uid}/{value}")
    public User updateDescription(@PathVariable String uid, @PathVariable String value) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("description", value);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    /**
     * 修改用户昵称
     * @param uid 用户uid
     * @param value 用户昵称
     * @return user 返回用户
     */
    @GetMapping(value = "/updateName/{uid}/{value}")
    public User updateNickName(@PathVariable String uid, @PathVariable String value) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("nickname", value);
        managerPageService.updateUser(map);

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/suspend/{uid}/{reason}")
    public User suspendUser(@PathVariable String uid, @PathVariable String reason) {
        int state = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        map.put("text", reason);
        map.put("date", new Date());
        managerPageService.updateUser(map);
        messageService.setMessage(map);

        return managerPageService.queryUserById(uid);
    }

    @GetMapping(value = "/restore/{uid}/{reason}")
    public User restoreUser(@PathVariable String uid, @PathVariable String reason) {
        int state = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("state", state);
        map.put("text", reason);
        map.put("date", new Date());
        managerPageService.updateUser(map);
        messageService.setMessage(map);

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

    @GetMapping(value = "/deleteSelect/{select}/{uidList}/{titles}")
    public int deleteSelect(@PathVariable List<Integer> select, @PathVariable List<String> uidList, @PathVariable List<String> titles) {

        for (int i = 0; i < uidList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uidList.get(i));
            map.put("text", "由于内容违规，您的创作《" + titles.get(i) + "》已被删除！");
            map.put("date", new Date());
            messageService.setMessage(map);
        }
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

        return new ManageContentPage(contentManageService.getNextContents(map));
    }

    @GetMapping(value = "/previousContent/{cid}/{date}")
    public ManageContentPage getPreviousContents(@PathVariable String cid, @PathVariable String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("start", date);

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
    public int ignoreReports(@PathVariable String uid, @PathVariable String cid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("cid", cid);

        return reportService.handleReports(map);
    }

    @GetMapping(value = "/handle/{rUid}/{title}")
    public int handleReports(@PathVariable String rUid, @PathVariable String title) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", rUid);
        map.put("cid", title);
        map.put("text", "您举报的内容《" + title + "》已经被删除，感谢您对社区管理作出的贡献！");
        map.put("date", new Date());

        messageService.setMessage(map);
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

    @GetMapping(value = "/systemMessage/{value}")
    public int sendAllUser(@PathVariable String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("text", value);
        map.put("date", new Date());

        return messageService.sendAllUser(map);
    }

    @GetMapping(value = "/sendMessage/{uid}/{value}")
    public int sendAllUser(@PathVariable String uid, @PathVariable String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("text", value);
        map.put("date", new Date());

        return messageService.setMessage(map);
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
