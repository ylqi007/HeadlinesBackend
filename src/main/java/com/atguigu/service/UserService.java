package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ylqi007
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-09-19 08:33:49
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     */
    Result login(User user);

    Result getUserInfo(String token);

    /**
     * 检查账号是否可用
     */
    Result checkUserName(String username);

    Result register(User user);

    Result checkLogin(String token);
}
