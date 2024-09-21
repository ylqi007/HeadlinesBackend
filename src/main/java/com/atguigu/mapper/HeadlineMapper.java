package com.atguigu.mapper;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.dto.PortalInput;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author ylqi007
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-09-19 08:33:49
* @Entity com.atguigu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage page, @Param("portalInput") PortalInput portalInput);

    Map queryDetailMap(Integer hid);

}




