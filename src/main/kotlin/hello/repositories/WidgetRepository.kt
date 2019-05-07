package hello.repositories

import hello.model.Widget
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo
import javax.inject.Singleton

@Singleton
class WidgetRepository(private val jdbi: Jdbi) {

    fun findById(id: String): Widget? {
        return jdbi.withHandle<Widget?, Nothing> { h ->
            h.createQuery("select * from widget where id = :id")
                .bind("id", id)
                .mapTo<Widget>()
                .findFirst()
                .orElse(null)
        }
    }

    fun insert(widget: Widget) {
        jdbi.withHandle<Widget, Nothing> { h ->
            h.createUpdate(
                """
                insert into widget
                (
                    id,
                    name
                )
                values
                (
                    :id,
                    :name
                )
                """.trimIndent()
            )
                .bindBean(widget)
                .execute()

            widget
        }
    }
}
