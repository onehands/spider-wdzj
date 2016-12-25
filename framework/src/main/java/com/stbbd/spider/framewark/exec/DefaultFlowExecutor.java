package com.stbbd.spider.framewark.exec;

import com.stbbd.spider.framewark.bean.Flow;
import com.stbbd.spider.framewark.bean.Step;
import com.stbbd.spider.framewark.enums.StepType;
import com.stbbd.spider.framewark.parser.DataTranslate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 朱国印 on 16-12-11.
 */
@Component
@Slf4j
public class DefaultFlowExecutor implements FlowExecutor {
    private static Map<StepType, StepExecutor> stepExecutorMap;
    @Autowired
    DataTranslate dataTranslate;
    public static void setStepExecutorMap(Map<StepType, StepExecutor> stepExecutorMap) {
        DefaultFlowExecutor.stepExecutorMap = stepExecutorMap;
    }

    public void execute(Flow flow) {
        List<Step> steps = flow.getSteps();
        if (steps == null || steps.isEmpty()) {
            log.info("步骤为空，不用继续执行");
            return;
        }
        for (Step step : steps) {
            executeStep(step);
        }

        //所有数据爬取完成，开始解析数据
        dataTranslate.translate();
    }

    private void executeStep(Step step) {
        StepType stepType = StepType.valueOf(step.getType());
        StepExecutor stepExecutor = stepExecutorMap.get(stepType);
        if (stepExecutor == null) {
            log.error("不支持的步骤类型：" + step.getType());
            return;
        }
        stepExecutor.execute(step);
    }
}
