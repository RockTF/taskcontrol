package com.taskcontrol.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource

class V1__CreateUserEntity : BaseJavaMigration() {
    @Throws(Exception::class)
    override fun migrate(context: Context) {
        val dataSource = SingleConnectionDataSource(context.connection, true)
        val jdbcTemplate = JdbcTemplate(dataSource)

        jdbcTemplate.execute(
            """
            CREATE TABLE IF NOT EXISTS public.users (
                id UUID NOT NULL PRIMARY KEY,
                username VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(50) NOT NULL
            );
            """.trimIndent()
        )
    }
}
