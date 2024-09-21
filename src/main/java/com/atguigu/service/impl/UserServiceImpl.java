package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author ylqi007
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-09-19 08:33:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 实现登录业务
     *  1. 根据账号，查询用户对象 loginUser
     *  2. 如果账号为空，则查询失败，501
     *  3. 对比密码，如果失败，返回503 Error
     *  4. 根据用户Id生成一个token，--> 通过Result返回
     * @param user
     * @return Result封装
     */
    @Override
    public Result login(User user) {
        // 检查用户是否存在：根据账号，查询数据库
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if(loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        // 检验用户密码是否正确
        if(!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            // 登录成功

            // 根据用户ID生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            // 将token封装到result并返回
            Map data = Map.of("token", token);
            return Result.ok(data);
        }

        // 密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 1. 校验token是否在有效期
     * 2. 根据token解析出userId
     * 3. 根据userId查询数据
     * 4. 去掉密码，封装result，返回结果
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {

        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration) {
            // 失败，token过期，登录失效
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        Long userId = jwtHelper.getUserId(token);
        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map data = Map.of("loginUserInfo", user);
        return Result.ok(data);
    }

    /**
     * 检查账号是否可用
     *  1. 根据账号查询进行count查询
     *  2. count==0， 可用
     *  3. count>0, 不可用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(lambdaQueryWrapper);

        if(count == 0) {
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 用户注册
     *  1. 检查账号是否已经被注册
     *  2. 密码加密处理
     *  3. 将账号保存到数据库
     *  4. 返回结果
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(lambdaQueryWrapper);

        if(count > 0) {
            Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);

        return Result.ok(null);
    }

    @Override
    public Result checkLogin(String token) {
        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}




