package com.taskcontrol.application.usecase

import com.opencsv.CSVWriter
import com.taskcontrol.application.port.ITaskRepository
import com.taskcontrol.application.port.IUserRepository
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class CsvExportService(
    private val taskRepository: ITaskRepository,
    private val userRepository: IUserRepository
) {
    fun exportTasks(response: HttpServletResponse) {
        response.contentType = "text/csv"
        response.setHeader("Content-Disposition", "attachment; filename=\"tasks.csv\"")

        CSVWriter(response.writer).use { csvWriter ->
            csvWriter.writeNext(arrayOf("Task ID", "Title", "Status", "Description"))

            val tasks = taskRepository.findAll()
            tasks.forEach { task ->
                csvWriter.writeNext(arrayOf(task.id.toString(), task.title, task.status.name, task.description ?: ""))
            }
        }
    }

    fun exportUsers(response: HttpServletResponse) {
        response.contentType = "text/csv"
        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"")

        CSVWriter(response.writer).use { csvWriter ->
            csvWriter.writeNext(arrayOf("User ID", "Username", "Role"))

            val users = userRepository.findAll()
            users.forEach { user ->
                csvWriter.writeNext(arrayOf(user.id.toString(), user.username, user.role.name))
            }
        }
    }
}
