package hello.world.web

import hello.world.model.Widget
import hello.world.services.WidgetService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

@Controller(
    "/widgets",
    consumes = [MediaType.APPLICATION_JSON],
    produces = [MediaType.APPLICATION_JSON]
)
class WidgetController(private val widgets: WidgetService) {
    @Post
    fun create(request: CreateWidget): Widget = widgets.create(request.name)

    @Get("/{id}")
    fun findById(id: String): Widget? = widgets.findById(id)
}

data class CreateWidget(val name: String)