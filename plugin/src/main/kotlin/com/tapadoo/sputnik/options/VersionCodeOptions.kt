package com.tapadoo.sputnik.options

import com.tapadoo.sputnik.utils.GitUtils
import org.gradle.api.Project
import java.util.*

/**
 * Used to configure how the versionCode of the project is generated.
 *
 * <pre>
 * sputnik {
 *   versionCode {
 *     baseValue 1
 *     codeType com.tapadoo.sputnik.VersionCodeType.TEAMCITY
 *   }
 * }
 * </pre>
 *
 * @author Elliot Tormey
 * @since 28/10/2016
 */
class VersionCodeOptions(private val project: Project) {
    private val gitUtils by lazy { GitUtils() }
    private var versionCodeType: VersionCodeType = VersionCodeType.DEFAULT
    var baseValue = 1

    fun codeType(versionCodeType: VersionCodeType) {
        this.versionCodeType = versionCodeType
    }

    fun baseValue(baseValue: Int) {
        this.baseValue = baseValue
    }

    /**
     * Calculates the version code for the project. If the [VersionCodeType] is not [VersionCodeType.DEFAULT]
     * check first if its a TeamCity build, if not check if it has any git commits that contain a pull
     * request number. If this fails the baseValue will get used. Otherwise respect the [VersionCodeType]
     * defined.
     */
    fun getVersionCode(): Int {
        var versionCode: Int = baseValue

        if (versionCodeType != VersionCodeType.DEFAULT) {
            if (versionCodeType == VersionCodeType.TEAMCITY && isTeamCityBuild()) {
                versionCode = getTeamCityBuild()
            } else if (versionCodeType == VersionCodeType.GIT) {
                versionCode = gitUtils.getPullRequestNumber(baseValue)
            }
        } else {
            if (isTeamCityBuild()) {
                versionCode = getTeamCityBuild()
            }
        }
        return versionCode
    }

    /**
     * Check if the current build is from TeamCity
     */
    private fun isTeamCityBuild(): Boolean = project.hasProperty("teamcity")

    /**
     * If this is a TeamCity build this method will return the build number specified from TeamCity of the current build.
     * Else it will return the baseValue.
     */
    private fun getTeamCityBuild(): Int {
        if (isTeamCityBuild()) {
            return Integer.parseInt((project.property("teamcity") as Properties)["build.number"] as String)
        } else {
            return baseValue
        }
    }
}