package com.bluurr.quora.client.provider

import com.bluurr.quora.client.QuoraClient
import org.springframework.stereotype.Component
import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque

/**
 * A simple blocking provider for allowing multiple requests at the same time.
 */
@Component
class BlockingQuoraClientProvider(clients: List<QuoraClient>) : QuoraClientProvider {

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

    private fun acquire(): QuoraClient {
        // Take the last client that was used
        return pool.takeLast()
    }

    private fun release(client: QuoraClient) {
        pool.put(client)
    }
}
