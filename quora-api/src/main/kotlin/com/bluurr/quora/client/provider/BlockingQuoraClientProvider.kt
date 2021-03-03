package com.bluurr.quora.client.provider

import com.bluurr.quora.client.QuoraClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque
import javax.annotation.PreDestroy

/**
 * A simple blocking provider for allowing multiple requests at the same time.
 */
@Component
class BlockingQuoraClientProvider(private val clients: List<QuoraClient>) : QuoraClientProvider {

    private val log: Logger = getLogger(this.javaClass)

    private val pool: BlockingDeque<QuoraClient>

    init {
        pool = LinkedBlockingDeque(clients)
    }

    override fun <T> client(block: (QuoraClient) -> T): T {

        val client = acquire()

        try {
            return block(client)
        } finally {
            release(client)
        }
    }

    @PreDestroy
    fun close() {

        log.info("Closing client pool for ${clients.size} clients")

        clients.forEach {
            it.close()
        }
    }

    private fun acquire(): QuoraClient {
        // Take the last client that was used
        return pool.takeLast()
    }

    private fun release(client: QuoraClient) {
        pool.put(client)
    }
}
