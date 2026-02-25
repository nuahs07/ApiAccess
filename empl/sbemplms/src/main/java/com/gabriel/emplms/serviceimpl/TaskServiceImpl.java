package com.gabriel.emplms.serviceimpl;

import com.gabriel.emplms.entity.TaskData;
import com.gabriel.emplms.model.Task;
import com.gabriel.emplms.repository.TaskDataRepository;
import com.gabriel.emplms.service.TaskService;
import com.gabriel.emplms.transform.TransformTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskDataRepository taskDataRepository;

    @Autowired
    TransformTaskService transformTaskService;

    @Override
    public Task[] getAll() {
        List<TaskData> taskDataList = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        taskDataRepository.findAll().forEach(taskDataList::add);
        Iterator<TaskData> it = taskDataList.iterator();
        while (it.hasNext()) {
            TaskData taskData = it.next();
            Task task = transformTaskService.transform(taskData);
            tasks.add(task);
        }
        return tasks.toArray(new Task[0]);
    }

    @Override
    public Task create(Task task) {
        logger.info("add:Input " + task.toString());
        TaskData taskData = transformTaskService.transform(task);
        taskData = taskDataRepository.save(taskData);
        logger.info("add:Created " + taskData.toString());
        return transformTaskService.transform(taskData);
    }

    @Override
    public Task update(Task task) {
        TaskData taskData = transformTaskService.transform(task);
        taskData = taskDataRepository.save(taskData);
        return transformTaskService.transform(taskData);
    }

    @Override
    public Task get(Integer id) {
        logger.info("Input id >> " + id);
        Optional<TaskData> optional = taskDataRepository.findById(id);
        if (optional.isPresent()) {
            TaskData taskData = optional.get();
            return transformTaskService.transform(taskData);
        }
        logger.info("Failed >> unable to locate id: " + id);
        return null;
    }

    @Override
    public void delete(Integer id) {
        logger.info("Input >> " + id);
        Optional<TaskData> optional = taskDataRepository.findById(id);
        if (optional.isPresent()) {
            TaskData taskData = optional.get();
            taskDataRepository.delete(taskData);
            logger.info("Success >> " + taskData.toString());
        } else {
            logger.info("Failed >> unable to locate task id: " + id);
        }
    }
}
