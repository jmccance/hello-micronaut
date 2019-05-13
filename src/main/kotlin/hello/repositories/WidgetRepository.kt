package hello.repositories

import hello.model.Widget
import hello.orNull
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import javax.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.kotlin.withHandleUnchecked

@Singleton
class WidgetRepository(private val jdbi: Jdbi, private val registry: MeterRegistry) {

    private fun queryTimer(query: String) =
        Timer.builder("database_query_duration_seconds")
            .publishPercentileHistogram()
            .tag("query", query)
            .register(registry)

    fun findById(id: String): Widget? {
        // withHandleUnchecked is an extension method provided by jdbi3-kotlin to work around
        // an issue where Kotlin can't infer the types for the normal withHandle method.
        //
        // See https://github.com/jdbi/jdbi/issues/858
        return queryTimer("findById").recordCallable {
            jdbi.withHandleUnchecked { h ->
                h.createQuery("select * from widget where id = :id")
                    .bind("id", id)
                    .mapTo<Widget>() // Nice jdbi3-kotlin extension method with reified types
                    .findFirst()
                    .orNull()
            }
        }
    }

    fun insert(widget: Widget) {
        queryTimer("insert").record {
            jdbi.withHandleUnchecked { h ->
                h.createUpdate(
                    """
                    insert into widget (id, name)
                    values (:id, :name)
                    """.trimIndent()
                )
                    .bindBean(widget)
                    .execute()
            }
        }
    }
}
