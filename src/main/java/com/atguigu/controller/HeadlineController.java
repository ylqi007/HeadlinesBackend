package com.atguigu.controller;

import com.atguigu.mapper.HeadlineMapper;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 9/21/24 16:57
 */
@RestController
@RequestMapping("headline")
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;

    // 登录之后才可以发布
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token) {
        // 检查登录 --> 通过拦截器统一检查登录
        return headlineService.publish(headline, token);
    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineService.getById(hid);

        Map data = new HashMap();
        data.put("headline", headline);

        return Result.ok(data);
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline) {
        return headlineService.updateHeadline(headline);
    }
}
