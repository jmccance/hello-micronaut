package hello.web

import hello.model.Widget
import hello.services.WidgetService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

/**
 * Example controller that can create and get Widgets.
 *
 */
// Note that the default content type for Micronaut is application/json, so we don't need to specify
// that here. Just the base path.
//
// No @Inject either; @Controller implies that behavior.
@Controller("/widgets")
class WidgetController(private val widgets: WidgetService) {

    data class CreateWidget(val name: String)

    // Request bodies naturally deserialize from the JSON, as you'd expect.
    @Post
    fun create(request: CreateWidget): Widget = widgets.create(request.name)

    // Automatically maps the path param to the method param of the same name.
    //
    // Kotlin T? types work great here; the framework automatically returns a 404 if you return
    // null.
    @Get("/{id}")
    fun findById(id: String): Widget? = widgets.findById(id)
}
