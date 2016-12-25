package com.stbbd.spider.framewark.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 朱国印 on 16-12-11.
 */
public class Flow {
    private List<Step> steps;

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void addStep(Step step) {
        if (steps == null) {
            steps = new ArrayList<Step>();
        }
        steps.add(step);
    }
}
