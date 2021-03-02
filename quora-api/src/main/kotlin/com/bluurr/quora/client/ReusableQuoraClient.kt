package com.bluurr.quora.client

import java.util.function.Consumer

class ReusableQuoraClient(client: QuoraClient, private val onClose: Consumer<QuoraClient>) : QuoraClient by client {

    override fun close() {

        onClose.accept(this)
    }
}
