package main.biggreenbook.controller;

import main.biggreenbook.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信用户相关控制器
 */
@RestController
@RequestMapping("/user")
public class WxUserController {

    @GetMapping("/login")
    public String login(@RequestParam("code") String code) {
        return wxUserService.login(code);
        //todo: 将用户信息放到session
    }

    @GetMapping("/autologin")
    public boolean autoLogin(@RequestParam("customCode") String customCode) {
        return wxUserService.tryLoginWithCustomCode(customCode);
        //todo: 将用户信息放到session
    }

    @Autowired
    private WxUserService wxUserService;
}
