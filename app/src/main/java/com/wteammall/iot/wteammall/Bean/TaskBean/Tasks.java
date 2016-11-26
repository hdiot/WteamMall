package com.wteammall.iot.wteammall.Bean.TaskBean;

import java.util.List;

/**
 * Created by I0T on 2016/11/24.
 */
public class Tasks {
    List<TaskDetail> TaskDetailList;

    public List<TaskDetail> getTaskList() {
        return TaskDetailList;
    }

    public void setTaskList(List<TaskDetail> taskList) {
        TaskDetailList = taskList;
    }

}
