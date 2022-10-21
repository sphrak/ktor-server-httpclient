package com.example

import com.example.plugins.LookupRequestDto
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `test httpclient to call external service`() = testApplication {

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        application {
            module(client)
        }

        externalServices {
            hosts("https://example.tld") {
                routing {
                    get("say") {
                        call.respondText("hi its me")
                    }
                }
            }
        }

        client.post("/lookup") {
            setBody(LookupRequestDto("https://example.tld"))
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("hi its me", bodyAsText())
        }
    }

}