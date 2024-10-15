package com.taskcontrol.api.controller

import com.taskcontrol.application.usecase.CsvExportService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Export", description = "Export API")
@RestController
@RequestMapping("/export")
class ExportController(private val csvExportService: CsvExportService) {

    @GetMapping("/tasks")
    fun exportTasks(response: HttpServletResponse) {
        csvExportService.exportTasks(response)
    }

    @GetMapping("/users")
    fun exportUsers(response: HttpServletResponse) {
        csvExportService.exportUsers(response)
    }
}
