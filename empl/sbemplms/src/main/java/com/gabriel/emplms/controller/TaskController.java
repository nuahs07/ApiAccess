package com.gabriel.emplms.controller;

import com.gabriel.emplms.model.Task;
import com.gabriel.emplms.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping("/api/task")
    public ResponseEntity<?> listTasks() {
        HttpHeaders headers = new HttpHeaders();
        try {
            Task[] tasks = taskService.getAll();
            return ResponseEntity.ok().headers(headers).body(tasks);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/api/task/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        logger.info("Input task id >> " + id);
        try {
            Task task = taskService.get(id);
            if (task != null) {
                return ResponseEntity.ok(task);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/api/task")
    public ResponseEntity<?> add(@RequestBody Task task) {
        logger.info("Input >> " + task.toString());
        try {
            Task newTask = taskService.create(task);
            logger.info("created task >> " + newTask.toString());
            return ResponseEntity.ok(newTask);
        } catch (Exception ex) {
            logger.error("Failed to create task: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/api/task")
    public ResponseEntity<?> update(@RequestBody Task task) {
        logger.info("Update Input >> " + task.toString());
        try {
            Task updatedTask = taskService.update(task);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception ex) {
            logger.error("Failed to update task: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/api/task/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        logger.info("Input >> " + id);
        try {
            taskService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
