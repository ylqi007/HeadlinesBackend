package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.dto.PortalInput;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ylqi007
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-09-19 08:33:49
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalInput portalInput);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateHeadline(Headline headline);
}
