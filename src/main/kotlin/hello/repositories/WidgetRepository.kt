package hello.repositories

import hello.model.Widget
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.kotlin.withHandleUnchecked
import javax.inject.Singleton

@Singleton
class WidgetRepository(private val jdbi: Jdbi) {

    fun findById(id: String): Widget? {
        // withHandleUnchecked is an extension method provided by jdbi3-kotlin to work around
        // an issue where Kotlin can't infer the types for the normal withHandle method.
        //
        // See https://github.com/jdbi/jdbi/issues/858
        return jdbi.withHandleUnchecked { h ->
            h.createQuery("select * from widget where id = :id")
                .bind("id", id)
                .mapTo<Widget>() // Nice jdbi3-kotlin extension method with reified types
                .findFirst()
                .orElse(null)
        }
    }

    fun insert(widget: Widget) {
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
