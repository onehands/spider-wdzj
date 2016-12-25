package com.stbbd.spider.wdzj.entities;

import com.stbbd.spider.wdzj.entities.task.SpiderTask;
import lombok.Data;

/**
 * Created by lei on 16-12-22.
 */
@Data
public class SourceData {
    private int platId;
    private SpiderTask.TaskType type;
    private String url;
    private String content;
    private String flag;
}
