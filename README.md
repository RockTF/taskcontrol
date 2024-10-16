TaskControl - це проект на платформі Spring Boot, реалізований на мові програмування Kotlin. В основі даного проекту лежить база даних PostgreSQL, використовуючи Flyway для управління міграціями бази даних. Проект розроблений для управління задачами в рамках корпоративного середовища, включаючи аутентифікацію та реєстрацію користувачів, адміністрування та роботу з задачами.

Структура проекту
Проект розділений на декілька основних пакетів, що відображають логічну структуру системи:

api: Містить контролери та маппери, які перетворюють доменні об'єкти (DO) в транспортні об'єкти (DTO) і навпаки.
application: Включає пакети model, port, і usecase.
model: Визначає доменні моделі.
port: Забезпечує реалізацію принципу інверсії залежностей для репозиторіїв, які взаємодіють із JPA репозиторіями.
usecase: Вміщує класи, що відповідають за бізнес-логіку.
config: Містить загальні конфігурації проекту, включно з налаштуваннями безпеки.
domain: Визначає DTO.
migration: Містить міграції Flyway для бази даних.
repository:
entity: Містить JPA ентіті для роботи з базою даних.
mapper: Забезпечує перетворення між Entity і DO.
Інтерфейси JPA для взаємодії з базою даних.
Контролери
Authentication and Registration Controller: Управління аутентифікацією та реєстрацією користувачів.
Task Controller: Робота з задачами.
Admin Controller: Функціонал для адміністраторів.
Початок роботи
Попередні вимоги
Java JDK 11 або вище.
PostgreSQL, налаштований і готовий до використання.
Налаштування бази даних
Перед початком роботи з проектом, налаштуйте вашу базу даних:

properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/taskcontrol
spring.datasource.username=postgres
spring.datasource.password=root
Замініть ці параметри на ваші власні налаштування доступу до бази даних.

Запуск проекту
Для запуску проекту склонуйте репозиторій і виконайте наступну команду:

bash
Copy code
./gradlew bootRun
Це автоматично запустить міграції Flyway, які налаштують структуру бази даних, і проект буде доступний на localhost:8080.

Ліцензія
Проект розповсюджується під ліцензією Apache 2.0. Повний текст ліцензії можна знайти в файлі LICENSE у кореневій директорії проекту.

Підтримка
Для отримання додаткової інформації та підтримки звертайтесь до розділу Issues у репозиторії проекту на GitHub.

З цією технічною документацією ви повинні мати всю необхідну інформацію для розуміння та використання проекту TaskControl.
