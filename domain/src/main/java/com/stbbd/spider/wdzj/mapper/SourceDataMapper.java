package com.stbbd.spider.wdzj.mapper;

import com.stbbd.spider.wdzj.entities.SourceData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lei on 16-12-24.
 */

@Mapper
public interface SourceDataMapper {

    @Select("select * from SourceData ")
    List<SourceData> findAll();

    @Select("select * from SourceData where platId = #{platId}")
    List<SourceData> findAllByPlatId(SourceData task);


    @Insert("INSERT INTO SourceData (`platId`, `type`, `url`, `content`, `flag`) " +
            "VALUES " +
            "(#{platId}, #{type}, #{url}, #{content}, #{flag});")
    int save(SourceData task);
}
