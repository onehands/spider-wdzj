package com.stbbd.spider.wdzj.entities.task;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by lei on 16-12-21.
 */
@Data
@MappedJdbcTypes(value = {JdbcType.DATE}, includeNullJdbcType = true)
public class SpiderTask implements Serializable {
    private TaskType taskType;
    private boolean done;
    private boolean init;
    private boolean pending;
    private boolean needRefresh;
    private Integer id;

    private LocalDateTime updateTime;

    public enum TaskType {
        DANGAN,
        DANGANPAGE,
        BASICINFO,
        DAYS30,
        PLAT_PAGE,
        FENBU,
        LILV,
        INITIALIZE,
        TARGET,
        INDUSTRY,
        INDEX,
        CUSTOM,
        PROBLEM,
        MESSAGE,
        PINGJI
    }
}
