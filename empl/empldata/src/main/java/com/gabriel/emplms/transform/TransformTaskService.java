package com.gabriel.emplms.transform;

import com.gabriel.emplms.entity.TaskData;
import com.gabriel.emplms.model.Task;

public interface TransformTaskService {
    TaskData transform(Task task);
    Task transform(TaskData taskData);
}
