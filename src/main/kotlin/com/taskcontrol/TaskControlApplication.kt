package com.taskcontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskControlApplication

fun main(args: Array<String>) {
    runApplication<TaskControlApplication>(*args)
}
