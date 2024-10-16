package com.taskcontrol.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource

class V3__ChangeDeadlineToDate : BaseJavaMigration() {
    @Throws(Exception::class)
    override fun migrate(context: Context) {
        val dataSource = SingleConnectionDataSource(context.connection, true)
        val jdbcTemplate = JdbcTemplate(dataSource)

        jdbcTemplate.execute(
            """
            ALTER TABLE public.tasks
            ALTER COLUMN deadline TYPE DATE USING deadline::date;
            """.trimIndent()
        )
    }
}