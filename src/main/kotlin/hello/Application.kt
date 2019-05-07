package hello

import io.micronaut.context.annotation.Factory
import io.micronaut.runtime.Micronaut
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import javax.inject.Singleton
import javax.sql.DataSource

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("hello.world")
            .mainClass(Application.javaClass)
            .start()
    }
}

@Factory
class ComponentFactory {
    @Singleton
    fun jdbi(ds: DataSource): Jdbi =
        with(Jdbi.create(ds)) {
            installPlugin(KotlinPlugin())
        }
}
