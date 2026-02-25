package com.gabriel.employeeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel.employeeapp.data.model.Task
import com.gabriel.employeeapp.data.remote.TaskApi
import com.gabriel.employeeapp.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TaskScreen {
    object List : TaskScreen()
    data class Detail(val task: Task) : TaskScreen()
    object Create : TaskScreen()
    data class Edit(val task: Task) : TaskScreen()
}

class TaskViewModel : ViewModel() {
    private val taskApi = RetrofitClient.instance.create(TaskApi::class.java)

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _currentScreen = MutableStateFlow<TaskScreen>(TaskScreen.List)
    val currentScreen: StateFlow<TaskScreen> = _currentScreen.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _tasks.value = taskApi.getTasks()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load tasks: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun showDetail(task: Task) {
        _currentScreen.value = TaskScreen.Detail(task)
    }

    fun showCreate() {
        _currentScreen.value = TaskScreen.Create
    }

    fun showEdit(task: Task) {
        _currentScreen.value = TaskScreen.Edit(task)
    }

    fun showList() {
        _currentScreen.value = TaskScreen.List
        fetchTasks()
    }

    fun createTask(title: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val newTask = taskApi.createTask(Task(id = 0, title = title, description = description))
                _tasks.value = _tasks.value + newTask
                _currentScreen.value = TaskScreen.List
            } catch (e: Exception) {
                _errorMessage.value = "Failed to create: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val updated = taskApi.updateTask(task)
                _tasks.value = _tasks.value.map { if (it.id == task.id) updated else it }
                _currentScreen.value = TaskScreen.List
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                taskApi.deleteTask(task.id)
                _tasks.value = _tasks.value.filter { it.id != task.id }
                _currentScreen.value = TaskScreen.List
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
