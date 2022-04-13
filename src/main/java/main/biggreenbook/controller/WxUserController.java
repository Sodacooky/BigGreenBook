package main.biggreenbook.controller;

import main.biggreenbook.utils.WxInfoContainer;
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


    }


    @Autowired
    private WxInfoContainer wxInfoContainer;

}
