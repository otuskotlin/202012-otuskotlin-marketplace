package ru.otus.otuskotlin.marketplace.backend.app.ktor.ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers

import io.ktor.http.cio.websocket.*
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IUserSession

class WsUserSession(
    override val fwSession: WebSocketSession
) : IUserSession<WebSocketSession> {
}
