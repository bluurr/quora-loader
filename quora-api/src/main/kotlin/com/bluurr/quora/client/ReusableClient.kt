package com.bluurr.quora.client

import java.util.function.Consumer

class ReusableClient(client: QuoraClient, private val onClose: Consumer<QuoraClient>) : QuoraClient by client {

    override fun close() {

        onClose.accept(this)
    }
}
