package com.gabriel.employeeapp.domain.repository

import com.gabriel.employeeapp.data.model.Task

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun getTask(id: Int): Task?
    suspend fun createTask(task: Task): Task?
    suspend fun updateTask(task: Task): Task?
    suspend fun deleteTask(id: Int): Boolean
}
