package com.stbbd.spider.framewark.handler;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import com.stbbd.spider.wdzj.mapper.PlatInfoMapper;
import com.stbbd.spider.wdzj.mapper.SourceDataMapper;
import com.stbbd.spider.wdzj.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 朱国印 on 16-12-12.
 */
@Slf4j
public abstract class HttpClientHandler {
    private static TaskMapper taskMapper;
    private static PlatInfoMapper platInfoMapper;
    private static SourceDataMapper sourceDataMapper;

    public SourceDataMapper getSourceDataMapper() {
        return sourceDataMapper;
    }

    public static void setSourceDataMapper(SourceDataMapper sourceDataMapper) {
        HttpClientHandler.sourceDataMapper = sourceDataMapper;
    }

    public Executor pool = Executors.newFixedThreadPool(30);
    public ObjectMapper objectMapper = new ObjectMapper();

    public abstract void excute(String result) throws Exception;


    public static void setTaskMapper(TaskMapper taskMapperNew) {
        taskMapper = taskMapperNew;
    }

    public PlatInfoMapper getPlatInfoMapper() {
        return platInfoMapper;
    }

    public static void setPlatInfoMapper(PlatInfoMapper taskMapperNew) {
        platInfoMapper = taskMapperNew;
    }

    public TaskMapper getTaskMapper() {
        return taskMapper;
    }

    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public void updateTask(SpiderTask task) {
        if (task.getId() == null) {
            this.taskMapper.save(task);
        } else {
            this.taskMapper.updateStatusById(task);
        }

    }

    public synchronized boolean locktask(SpiderTask.TaskType taskType) {
        List<SpiderTask> tasks = taskMapper.findByTaskType(taskType);
        if (null == tasks || tasks.size() == 0) {
            SpiderTask task = new SpiderTask();
            task.setTaskType(taskType);
            task.setPending(true);
            task.setUpdateTime(LocalDateTime.now());
            try {
                taskMapper.save(task);
                return true;
            } catch (Exception e) {
                log.error("lock task error ", e);
                return false;
            }
        } else {
            SpiderTask task = tasks.get(0);
            if (isTimeout(task) || (!isTimeout(task) && !task.isDone() && !task.isPending())) {
                task.setPending(true);
                task.setUpdateTime(LocalDateTime.now());
                taskMapper.updateStatusById(task);
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean isTimeout(SpiderTask task) {
        return task.getUpdateTime().plusDays(2).isBefore(LocalDateTime.now());
    }


    public synchronized void releaseTask(SpiderTask.TaskType taskType) {
        SpiderTask task = taskMapper.findByTaskType(taskType).get(0);
        if (null == task) {
            task = new SpiderTask();
            task.setTaskType(taskType);
        }
        task.setPending(false);
        task.setDone(true);
        task.setUpdateTime(LocalDateTime.now());
        this.updateTask(task);
    }

}
