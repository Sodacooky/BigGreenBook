package main.biggreenbook.controller;

import main.biggreenbook.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信用户相关控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/usr")
public class WxUserController {

    /**
     * 登录
     *
     * @param code 来自小程序wx.login的jscode
     * @return 自定义登录记录，用于近期自动登录
     */
    @GetMapping("/login")
    public String login(@RequestParam("code") String code) {
        return wxUserService.login(code);
        //todo: 将用户信息放到session
    }

    /**
     * 尝试使用自定义登录记录进行自动登录
     *
     * @param customCode 自定义登录记录
     * @return 是否成功
     */
    @GetMapping("/autologin")
    public boolean autoLogin(@RequestParam("customCode") String customCode) {
        return wxUserService.tryLoginWithCustomCode(customCode);
        //todo: 将用户信息放到session
    }

    /**
     * 登出
     *
     * @return 如果本来就没登录那么返回假
     */
    @GetMapping("/logout")
    public boolean logout() {
        //todo：将session中的用户信息删除
        return true;
    }

    @Autowired
    private WxUserService wxUserService;
}
