package hello.world.services

import hello.world.model.Widget
import java.util.*
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class WidgetService {
    private val widgets: MutableMap<String, Widget> = mutableMapOf(
        "123" to Widget("123", "Sprocket"),
        "abc" to Widget("abc", "Flange")
    )

    fun create(name: String): Widget {
        val id = UUID.randomUUID().toString()
        val widget = Widget(id, name)

        widgets[id] = widget

        return widget
    }

    fun findById(id: String): Widget? = widgets[id]
}