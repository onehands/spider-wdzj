package com.stbbd.spider.wdzj.mapper;

import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lei on 16-12-24.
 */

@Mapper
public interface TaskMapper {

    @Select("select * from SpiderTask ")
    List<SpiderTask> findAll();

    @Select("select * from SpiderTask where taskType= #{taskType} order by updateTime Desc ")
    List<SpiderTask> findByTaskType(SpiderTask.TaskType TaskType);

    @Update("update  SpiderTask set done=#{done},pending=#{pending} where taskType= #{taskType}")
    int updateStatusByTaskType(SpiderTask.TaskType TaskType);

    @Update("update  SpiderTask set done=#{done},pending=#{pending} where id= #{id}")
    int updateStatusById(SpiderTask task);


    @Insert("INSERT INTO SpiderTask (taskType, done, init, pending, needRefresh, updateTime)\n" +
            "        VALUES\n" +
            "        (#{taskType}, #{done}, #{init}, #{pending}, #{needRefresh}, #{updateTime})")
    int save(SpiderTask task);
}
