package com.example

import io.ktor.server.application.*
import com.example.plugins.*
import io.ktor.client.HttpClient
import io.ktor.server.cio.CIO

fun main(args: Array<String>): Unit =
    io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(httpClient: HttpClient = HttpClient(io.ktor.client.engine.cio.CIO)) {
    configureRouting(httpClient = httpClient)
    configureHTTP()
    configureSerialization()
}
