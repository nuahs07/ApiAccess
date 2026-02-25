package com.gabriel.employeeapp.data.repository

import com.gabriel.employeeapp.data.model.Task
import com.gabriel.employeeapp.data.remote.TaskApi
import com.gabriel.employeeapp.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskApi: TaskApi
) : TaskRepository {
    override suspend fun getTasks(): List<Task> {
        return taskApi.getTasks()
    }

    override suspend fun getTask(id: Int): Task? {
        return try {
            taskApi.getTask(id)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createTask(task: Task): Task? {
        return try {
            taskApi.createTask(task)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateTask(task: Task): Task? {
        return try {
            taskApi.updateTask(task)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteTask(id: Int): Boolean {
        return try {
            val response = taskApi.deleteTask(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
