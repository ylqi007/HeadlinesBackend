package com.atguigu.service;

import com.atguigu.pojo.Type;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ylqi007
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-09-19 08:33:49
*/
public interface TypeService extends IService<Type> {

    Result findAllTypes();
}
