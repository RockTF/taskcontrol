src
 ├── api
 │    ├── controller
 │    │    ├── TaskController.kt
 │    │    └── UserController.kt
 │    └── mapper
 │         └── TaskMapper.kt
 ├── application
 │    ├── model
 │    │    ├── Task.kt
 │    │    └── User.kt
 │    ├── port
 │    │    ├── TaskRepository.kt
 │    │    └── UserRepository.kt
 │    └── usecase
 │         ├── CreateTaskUseCase.kt
 │         ├── GetTasksUseCase.kt
 │         └── ManageUsersUseCase.kt
 ├── config
 │    └── SecurityConfig.kt
 ├── domain
 │    └── calculation
 │         ├── TaskStatisticsCalculator.kt
 ├── event
 ├── repository
 │    ├── jpa
 │    │    ├── entity
 │    │    │    ├── TaskEntity.kt
 │    │    │    └── UserEntity.kt
 │    │    ├── TaskJpaRepository.kt
 │    │    └── UserJpaRepository.kt
 └── resources
      └── application.yml
