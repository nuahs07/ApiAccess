package com.gabriel.emplms.transform;

import com.gabriel.emplms.entity.TaskData;
import com.gabriel.emplms.model.Task;
import org.springframework.stereotype.Service;

@Service
public class TransformTaskServiceImpl implements TransformTaskService {
    @Override
    public TaskData transform(Task task) {
        TaskData taskData = new TaskData();
        taskData.setId(task.getId());
        taskData.setTitle(task.getTitle());
        taskData.setDescription(task.getDescription());
        return taskData;
    }

    @Override
    public Task transform(TaskData taskData) {
        Task task = new Task();
        task.setId(taskData.getId());
        task.setTitle(taskData.getTitle());
        task.setDescription(taskData.getDescription());
        return task;
    }
}
