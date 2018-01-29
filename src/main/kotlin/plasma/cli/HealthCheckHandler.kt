package plasma.cli

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

/**
 *
 * @author soushin
 */
@Component
class HealthCheckHandler {

    val logger = LoggerFactory.getLogger(HealthCheckHandler::class.java)

    fun healthy(req: ServerRequest): Mono<ServerResponse> {
        return ok().json().body(Mono.just(true))
    }
}
