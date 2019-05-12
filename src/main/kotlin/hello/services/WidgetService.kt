package hello.services

import hello.model.Widget
import hello.repositories.WidgetRepository
import java.util.UUID
import javax.inject.Singleton

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
