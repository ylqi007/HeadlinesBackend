package com.atguigu.service.impl;

import com.atguigu.pojo.dto.PortalInput;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author ylqi007
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-09-19 08:33:49
*/
@Service
@Slf4j
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private HeadlineMapper headlineMapper;

    /**
     * 首页数据查询
     *  1. 进行分页数据查询
     *  2. 分页数据，凭借到result即可
     *
     * 注意：
     *  1. 查询需要自定义语句，自定义mapper的方法，携带分页
     *  2. 返回的结果List<Map>
     * @param portalInput
     * @return
     */
    @Override
    public Result findNewsPage(PortalInput portalInput) {
        // Page --> 要包含当前页数和页容量
        log.info("#### portalInput.getPageNum() = {}", portalInput.getPageNum());
        log.info("#### portalInput.getPageSize() = {}", portalInput.getPageSize());
        IPage<Map> page = new Page<>(portalInput.getPageNum(), portalInput.getPageSize());
        headlineMapper.selectMyPage(page, portalInput);

        List<Map> records = page.getRecords();

        log.info("#### page = {}", page);
        log.info("#### page.getRecords() = {}",  records);
        log.info("#### page.getRecords().size() = {}",  records.size());

        Map data = new HashMap();
        data.put("pageData", records);
        data.put("pageNum", page.getCurrent());
        data.put("pageSize", page.getSize());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo", data);
        return Result.ok(pageInfo);
    }

    /**
     * 根据ID检查详情
     *  1. 修改对应的阅读量，+1，乐观锁，注意当前数据的版本号
     *  2. 查询对应的数据即可，多表查询，头条和用户表
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline", data);

        // 修改阅读量，+1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        // headline.setVersion((Integer) data.get("version"));
        headline.setPageViews((Integer) data.get("pageViews") + 1);
        headlineMapper.updateById(headline);

        return Result.ok(headlineMap);
    }

    /**
     * 发布头条
     *  1. 补全数据
     *  2.
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {
        // token 查询userId
        int userId = jwtHelper.getUserId(token).intValue();

        // 数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 修改头条数据
     *  1. hid查询数据的最新version
     *  2. 修改数据时间为当前节点
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadline(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

//        headline.setVersion(version);   // 乐观锁
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




