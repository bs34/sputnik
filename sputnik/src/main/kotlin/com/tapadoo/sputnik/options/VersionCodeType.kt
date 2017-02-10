package com.tapadoo.sputnik.options

/**
 * @author Elliot Tormey
 * @since 07/11/2016
 */
enum class VersionCodeType {
    /** Using this type will return the baseValue. Defaults to '1' if not specified by the user.*/
    DEFAULT,
    /** Using this type will return the TeamCity build number.*/
    TEAMCITY,
    /** Using this will return the pull request number from the commit history of the project.*/
    GIT
}