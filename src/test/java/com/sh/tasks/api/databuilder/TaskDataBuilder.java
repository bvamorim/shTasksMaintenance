package com.sh.tasks.api.databuilder;

import com.sh.tasks.api.model.Task;

/**
 * <h1>TaskDataBuilder</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-24
 */
public class TaskDataBuilder {


    public static Task getTask(){

        Task task = new Task();
        task.setName("Task Name Test");
        task.setDescription("Task Description Test");

        return task;
    }
}
