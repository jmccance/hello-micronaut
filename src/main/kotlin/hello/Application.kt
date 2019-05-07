package hello

import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Configuration
import io.micronaut.context.annotation.Factory
import io.micronaut.runtime.Micronaut
import io.opentracing.contrib.jdbi3.OpentracingJdbi3Plugin
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import javax.inject.Singleton
import javax.sql.DataSource
import io.micronaut.management.endpoint.annotation.Read
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.micronaut.management.endpoint.annotation.Endpoint
import javax.inject.Inject



object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("hello.world")
            .mainClass(Application.javaClass)
            .start()
    }
}

/**
 * Configure any external beans required for the application.
 */
@Factory
class ComponentFactory {
    @Singleton
    fun jdbi(ds: DataSource): Jdbi =
        with(Jdbi.create(ds)) {
            installPlugin(KotlinPlugin())
            installPlugin(OpentracingJdbi3Plugin())
        }

    @Bean
    fun timedAspect(registry: MeterRegistry): TimedAspect = TimedAspect(registry)
}
