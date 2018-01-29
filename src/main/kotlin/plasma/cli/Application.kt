package plasma.cli

import io.grpc.ManagedChannel
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import openfresh.plasma.EventType
import openfresh.plasma.Payload
import openfresh.plasma.Request
import openfresh.plasma.StreamServiceGrpc
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Scope
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import javax.annotation.PostConstruct
import kotlin.reflect.KClass


/**
 *
 * @author soushin
 */
@SpringBootApplication
class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            run(Application::class, *args)
        }
    }
}

fun run(type: KClass<*>, vararg args: String) = SpringApplication.run(type.java, *args)
fun ServerResponse.BodyBuilder.json(): ServerResponse.BodyBuilder = contentType(MediaType.APPLICATION_JSON_UTF8)

@Component
@Scope("singleton")
class PlasmaCli(private val managedChannel: ManagedChannel,
                private val streamObservable: StreamObservable) {

    private val LOGGER = KotlinLogging.logger {}
    private val PLASMA_EVENT_TYPE = "my-event"

    @PostConstruct
    fun init() {
        try {
            val stub = StreamServiceGrpc.newStub(managedChannel)
            val event = EventType.newBuilder().setType(PLASMA_EVENT_TYPE).build()
            val request = Request.newBuilder().addEvents(event).build()

            stub.events(streamObservable).onNext(request)
        } catch (e: Exception) {
            LOGGER.error(e.message)
        }
    }

}

@Component
class StreamObservable : StreamObserver<Payload> {

    private val LOGGER = KotlinLogging.logger {}

    override fun onError(t: Throwable?) {
        LOGGER.info(String.format("stream observe: onError={%s}", t?.message ?: "error"))
    }

    override fun onCompleted() {
        LOGGER.info("stream observe: complete")
    }

    override fun onNext(value: Payload) {
        try {
            LOGGER.info(String.format("stream observe: onNext={%s}", value.data))
        } catch (e: Exception) {
            LOGGER.error(String.format("stream observe: onNext={%s}", e.message))
        }
    }
}
