package plasma.cli

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

/**
 *
 * @author soushin
 */
@Configuration
class HealthCheckRoute(private val healthCheckHandler: HealthCheckHandler) {

    @Bean
    fun healthCheckRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/health_check").nest {
            GET("/", healthCheckHandler::healthy)
        }
    }
}
