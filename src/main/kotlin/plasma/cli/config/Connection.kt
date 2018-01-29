package plasma.cli.config

import io.grpc.ManagedChannel
import io.grpc.netty.NettyChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author soushin
 */
@Configuration
class Connection {

    private val HOST = "localhost"
    private val PORT = 50051

    @Bean
    fun managedChannel(): ManagedChannel? {
        return NettyChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext(true)
                .build()
    }
}