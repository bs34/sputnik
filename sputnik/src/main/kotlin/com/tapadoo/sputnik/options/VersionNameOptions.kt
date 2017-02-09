package com.tapadoo.sputnik.options

import com.tapadoo.sputnik.utils.GitUtils
import org.gradle.api.GradleScriptException

/**
 * Used to configure how the versionName of the project is generated. Used can specify a
 * major, minor and patch version number to use. A build number is appended to the end of the versionName
 * automatically, user cannot change this version from here. Can only be done from the {@link VersionCodeOptions}
 *
 * <pre>
 * sputnik {
 *   versionName {
 *     versionMajor 1
 *     versionMinor 2
 *     versionPatch 3
 *     nameType com.tapadoo.sputnik.VersionNameType.GIT_HASH
 *   }
 * }
 * </pre>
 *
 * @author Elliot Tormey
 * @since 27/10/2016
 */
class VersionNameOptions(val versionCodeOptions: VersionCodeOptions) {
    private val versionBuild: Int by lazy { versionCodeOptions.getVersionCode() }

    private var versionMajor = 0
    private var versionMinor = -1
    private var versionPatch = -1
    private var versionNameType: VersionNameType = VersionNameType.DEFAULT

    var buildType: String = ""
    var baseValue: String = ""

    /** Major version must be greater the zero.*/
    fun versionMajor(versionMajor: Int) {
        this.versionMajor = versionMajor
    }

    private fun getVersionMajor(): String {
        if (versionMajor < 0) {
            throw GradleScriptException("nameOptions.versionMinor must not be less than 0", Throwable())
        }
        return versionMajor.toString()
    }

    /** Minor version must be greater the -1.*/
    fun versionMinor(versionMinor: Int) {
        this.versionMinor = versionMinor
    }

    private fun getVersionMinor(): String {
        if (versionMinor < -1) {
            throw GradleScriptException("nameOptions.versionMinor must not be less than 0", Throwable())
        }
        return if (versionMinor != -1) "." + versionMinor else ".0"
    }

    /** Patch version must be greater the -1.*/
    fun versionPatch(versionPatch: Int) {
        this.versionPatch = versionPatch
    }

    private fun getVersionPatch(): String {
        if (versionPatch < -1) {
            throw GradleScriptException("nameOptions.versionPatch must not be less than 0", Throwable())
        }
        return if (versionPatch != -1) "." + versionPatch else ""
    }

    private fun getVersionBuild(): String {
        return if (versionBuild > 1) "." + versionBuild else ""
    }

    fun nameType(versionNameType: VersionNameType) {
        this.versionNameType = versionNameType
    }

    fun baseValue(baseValue: String) {
        this.baseValue = baseValue
    }

    /**
     * Calculates the versionName for this project. If any of the version numbers do not meet the given criteria
     * the build will fail.
     */
    fun getVersionName(): String {
        var versionName: String = baseValue + getVersionBuild()
        if (baseValue.isEmpty()) {
            versionName = getVersionMajor() + getVersionMinor() + getVersionPatch() + getVersionBuild()

            if (versionNameType == VersionNameType.GIT_HASH) {
                versionName = GitUtils.getCommitHash()
            } else if (versionNameType == VersionNameType.GIT_HASH_PLUS_NAME) {
                versionName += ".${GitUtils.getCommitHash()}"
            }
        }

        // Need to print this so teamcity can set it as its current build number.
        println("##teamcity[buildNumber '$versionName']")
        return versionName
    }
}