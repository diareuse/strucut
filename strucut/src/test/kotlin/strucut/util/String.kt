package strucut.util

fun String.countOccurrence(substring: String): Int {
    var count = 0
    var slice = this
    var index = slice.indexOf(substring)
    while (index != -1) {
        slice = slice.slice(index + substring.length until slice.length)
        index = slice.indexOf(substring)
        count++
    }
    return count
}