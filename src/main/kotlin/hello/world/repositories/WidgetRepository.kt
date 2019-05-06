package hello.world.repositories

import hello.world.model.Widget
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.mapTo
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class WidgetRepository(dataSource: DataSource) {

    val jdbi = with(Jdbi.create(dataSource)) {
        installPlugin(KotlinPlugin())
    }

    fun findById(id: String): Widget? {
        return jdbi.withHandle<Widget?, Nothing> { h ->
            h.createQuery("select * from widget where id = :id")
                .mapTo<Widget>()
                .findFirst()
                .orElse(null)
        }
    }
}