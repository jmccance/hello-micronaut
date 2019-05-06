package hello.world.web

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/hello")
class HelloController {
    @Get(produces = [MediaType.TEXT_PLAIN])
    fun index(): String = "Greetings, meatbag"

    @Get("/{value}", produces = [MediaType.APPLICATION_JSON])
    fun getValue(value: String) = Value(value)
}

data class Value(val value: String)