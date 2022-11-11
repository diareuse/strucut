package strucut

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal typealias Topology = Map<String, Any?>

internal fun String.toTopology(): Iterable<Topology> = when {
    startsWith("[") -> toListTopology()
    startsWith("{") -> toSingleTopology().let(::listOf)
    else -> listOf(emptyMap())
}

private fun String.toSingleTopology(): Topology {
    val type = object : TypeToken<Topology>() {}.type
    val gson = Gson()
    return gson.fromJson(this, type)
}

private fun String.toListTopology(): List<Topology> {
    val type = object : TypeToken<List<Topology>>() {}.type
    val gson = Gson()
    return gson.fromJson(this, type)
}
