package com.taskcontrol.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource

class V2__CreateTaskEntity : BaseJavaMigration() {
    @Throws(Exception::class)
    override fun migrate(context: Context) {
        val dataSource = SingleConnectionDataSource(context.connection, true)
        val jdbcTemplate = JdbcTemplate(dataSource)

        jdbcTemplate.execute(
            """
            CREATE TABLE IF NOT EXISTS public.tasks (
                id UUID NOT NULL PRIMARY KEY,
                user_id UUID NOT NULL,
                title VARCHAR(255) NOT NULL,
                description TEXT,
                deadline TIMESTAMP,
                priority VARCHAR(50) NOT NULL,
                status VARCHAR(50) NOT NULL
            );

            ALTER TABLE public.tasks
            ADD CONSTRAINT fk_user_task
            FOREIGN KEY (user_id)
            REFERENCES public.users(id)
            ON DELETE CASCADE;
            """.trimIndent()
        )
    }
}
