@file:JvmName("DestinationKt")

package com.dani.starwars.navigation

import android.os.Bundle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

@JvmInline
value class Route(val value: String) {
    operator fun invoke() = value
}

val URL_CHARACTER_ENCODING: String = Charsets.UTF_8.name()

interface DestinationArgs

interface ArgumentDestination<T: DestinationArgs> {

    val name: String
        get() = this::class.simpleName!!

    val arg: String
        get() = "${name}_ARG"

    fun getRoute() = Route("$name/{$arg}")
}

inline fun <reified T: DestinationArgs> ArgumentDestination<T>.createRouteWithArgs(arg: T): Route {
    val encodedJson = URLEncoder.encode(Json.encodeToString(arg), URL_CHARACTER_ENCODING)
    return Route("$name/${encodedJson}")
}

inline fun <reified T: DestinationArgs> ArgumentDestination<T>.retrieveParams(args: Bundle?): T {
    val encodedJson = args?.getString(arg) ?: throw IllegalStateException("arg with key $arg was not found in the navigation args bundle")
    val argsJson = URLDecoder.decode(encodedJson, URL_CHARACTER_ENCODING)
    return Json.decodeFromString(argsJson)
}