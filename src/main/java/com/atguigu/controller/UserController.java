package com.atguigu.controller;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: UserController，与User相关的控制类。
 *
 * @Author: ylqi007
 * @Create: 9/20/24 19:46
 */
@RestController
@RequestMapping("user")
//@CrossOrigin    // 前端访问，需要跨域，此处还需要吗？
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        Result result = userService.getUserInfo(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username) {
        return userService.checkUserName(username);
    }

    @PostMapping("register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token) {

        return userService.checkLogin(token);
    }
}
