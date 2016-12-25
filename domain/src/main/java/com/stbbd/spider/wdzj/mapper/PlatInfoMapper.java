package com.stbbd.spider.wdzj.mapper;

import com.stbbd.spider.wdzj.entities.PlatInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lei on 16-12-24.
 */

@Mapper
public interface PlatInfoMapper {

    @Select("select * from PlatInfo ")
    List<PlatInfo> findAll();

    @Select("select * from PlatInfo where platId = #{platId} ")
    PlatInfo findByPlatId(PlatInfo platInfo);


    @Update("update PlatInfo set \n" +
            "allPlatPin = #{allPlatPin},\n" +
            "platPin = #{platPin},\n" +
            "platStatus = #{platStatus},\n" +
            "firstPin = #{firstPin},\n" +
            "platEarnings = #{platEarnings},\n" +
            "zonghezhishuRank = #{zonghezhishuRank},\n" +
            "onlineDate = #{onlineDate},\n" +
            "cityName = #{cityName},\n" +
            "platNamePin = #{platNamePin},\n" +
            "platIconUrl = #{platIconUrl},\n" +
            "gjlhhFlag = #{gjlhhFlag},\n" +
            "platName = #{platName},\n" +
            "tzjPj = #{tzjPj},\n" +
            "zonghezhishu = #{zonghezhishu} " +
            "where platId = #{platId}")
    int updateStatusById(PlatInfo task);


    @Insert("insert into PlatInfo (" +
            "allPlatPin,\n" +
            "platPin,\n" +
            "platStatus,\n" +
            "firstPin,\n" +
            "platEarnings,\n" +
            "zonghezhishuRank,\n" +
            "onlineDate,\n" +
            "cityName,\n" +
            "platNamePin,\n" +
            "platIconUrl,\n" +
            "gjlhhFlag,\n" +
            "platName,\n" +
            "platId,\n" +
            "tzjPj,\n" +
            "zonghezhishu)\n" +
            "values\n" +
            "(\n" +
            "#{allPlatPin},\n" +
            "#{platPin},\n" +
            "#{platStatus},\n" +
            "#{firstPin},\n" +
            "#{platEarnings},\n" +
            "#{zonghezhishuRank},\n" +
            "#{onlineDate},\n" +
            "#{cityName},\n" +
            "#{platNamePin},\n" +
            "#{platIconUrl},\n" +
            "#{gjlhhFlag},\n" +
            "#{platName},\n" +
            "#{platId},\n" +
            "#{tzjPj},\n" +
            "#{zonghezhishu}\n" +
            ")")
    int save(PlatInfo task);
}
