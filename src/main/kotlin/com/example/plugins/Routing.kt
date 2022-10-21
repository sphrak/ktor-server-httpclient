package com.example.plugins

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

fun Application.configureRouting(httpClient: HttpClient) {

    routing {
        post("/lookup") {

            val lookup = call.receive<LookupRequestDto>()

            val response: HttpResponse = httpClient.get(lookup.url)

            call.respond(HttpStatusCode.OK, response.bodyAsText())
        }
    }
}

@Serializable
data class LookupRequestDto(
    @SerialName(value = "url")
    val url: String
)
