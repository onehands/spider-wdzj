package com.stbbd.spider.wdzj.task.controller;

import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import com.stbbd.spider.wdzj.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lei on 16-12-20.
 */
@RestController
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
//        List<SpiderTask> tasks = taskMapper.findAll();
        List<SpiderTask> tasks2 = taskMapper.findByTaskType(SpiderTask.TaskType.TARGET);
        SpiderTask task = new SpiderTask();
        task.setUpdateTime(LocalDateTime.now());
        task.setTaskType(SpiderTask.TaskType.BASICINFO);
        task.setDone(true);
        int task2 = taskMapper.save(task);
        return "hello world";
    }

}
