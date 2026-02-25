package com.gabriel.employeeapp.data.remote

import com.gabriel.employeeapp.data.model.Task
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {
    @GET("api/task")
    suspend fun getTasks(): List<Task>

    @GET("api/task/{id}")
    suspend fun getTask(@Path("id") id: Int): Task

    @PUT("api/task")
    suspend fun createTask(@Body task: Task): Task

    @POST("api/task")
    suspend fun updateTask(@Body task: Task): Task

    @DELETE("api/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}
