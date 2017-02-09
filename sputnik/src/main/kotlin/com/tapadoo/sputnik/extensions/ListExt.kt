package com.tapadoo.sputnik.extensions

/**
 * Contains extensions that can be used on [List]s
 *
 * @author Elliot Tormey
 * @since 27/12/2016
 */

/**
 * Filter the list to remove items that match the 'exclude' list.
 */
internal fun <T> Iterable<T>.filterNotWith(exclude: Iterable<T>): List<T> {
    val filtered = this.toMutableList()
    for (element in exclude) {
        filtered.filterNot {
            contains(it)
        }
    }
    return filtered
}