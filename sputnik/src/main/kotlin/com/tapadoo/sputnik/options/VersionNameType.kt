package com.tapadoo.sputnik.options

/**
 * @author Elliot Tormey
 * @since 07/11/2016
 */
enum class VersionNameType {
    /** Using this will return the version numbers specified by the user.*/
    DEFAULT,
    /** Using this will return the short hand hash of the last commit.*/
    GIT_HASH,
    /** Using this will return the short hand hash of the last commit appended to the version name.*/
    GIT_HASH_PLUS_NAME
}