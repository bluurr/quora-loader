package com.bluurr.quora.client.provider

import com.bluurr.quora.client.QuoraClient
import com.bluurr.quora.client.ReusableClient
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

        val reusableClients = clients.map {

            ReusableClient(it, this::onRelease)
        }

        pool = LinkedBlockingDeque(reusableClients)
    }

    override fun get(): QuoraClient {
        // Take the last client that was used
        return pool.takeLast()
    }

    private fun onRelease(client: QuoraClient) {
        pool.put(client)
    }
}
