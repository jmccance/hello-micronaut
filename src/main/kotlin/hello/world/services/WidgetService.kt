package hello.world.services

import hello.world.model.Widget
import hello.world.repositories.WidgetRepository
import java.util.*
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class WidgetService(private val widgets: WidgetRepository) {
    fun create(name: String): Widget {
        val id = UUID.randomUUID().toString()
        val widget = Widget(id, name)

        widgets.insert(widget)

        return widget
    }

    fun findById(id: String): Widget? = widgets.findById(id)
}
