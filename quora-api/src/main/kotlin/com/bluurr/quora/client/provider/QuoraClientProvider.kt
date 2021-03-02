package com.bluurr.quora.client.provider

import com.bluurr.quora.client.QuoraClient

interface QuoraClientProvider {
    fun get() : QuoraClient;
}
