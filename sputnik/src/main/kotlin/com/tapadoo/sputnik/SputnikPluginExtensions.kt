package com.tapadoo.sputnik

import com.android.build.gradle.BaseExtension
import com.tapadoo.sputnik.options.FileOutputOptions
import com.tapadoo.sputnik.options.ProguardOptions
import com.tapadoo.sputnik.options.VersionCodeOptions
import com.tapadoo.sputnik.options.VersionNameOptions
import groovy.lang.Closure
import org.gradle.api.Project

/**
 * Where the user interacts with this plugin.
 *
 * @author Elliot Tormey
 * @since 27/10/2016
 */
open class SputnikPluginExtension(val project: Project) {
    private val android by lazy { project.extensions.getByName("android") as BaseExtension }

    val codeOptions: VersionCodeOptions by lazy { VersionCodeOptions(project) }
    val nameOptions: VersionNameOptions by lazy { VersionNameOptions(codeOptions) }
    val fileOptions: FileOutputOptions by lazy { FileOutputOptions(project) }
    val proguard: ProguardOptions by lazy { ProguardOptions(project, android) }
    // Quality check enabled by default.
    var enableQuality: Boolean = true

    fun versionName(closure: Closure<VersionNameOptions>) {
        project.configure(nameOptions, closure)
    }

    fun versionName(): String {
        return nameOptions.getVersionName()
    }

    fun versionName(baseValue: String) {
        nameOptions.baseValue = baseValue
        android.defaultConfig.versionName = nameOptions.getVersionName()
    }

    fun versionCode(closure: Closure<VersionCodeOptions>) {
        project.configure(codeOptions, closure)
    }

    fun versionCode(): Int {
        return codeOptions.getVersionCode()
    }

    fun versionCode(baseValue: Int) {
        codeOptions.baseValue = baseValue
        android.defaultConfig.versionCode = codeOptions.getVersionCode()
    }

    fun appName(appName: String) {
        fileOptions.appName = appName
    }

    fun apkName(closure: Closure<FileOutputOptions>) {
        project.configure(fileOptions, closure)
    }

    /**
     * Sets the APK naming format to use when generating an APK. See [@link FileOutputOptions]
     *
     * @param nameFormat The name format to use.
     */
    fun apkName(nameFormat: String) {
        fileOptions.nameFormat = nameFormat
        fileOptions.generateOutputName()
    }

    /**
     * Enables or disables all quality checks when building a project with this plugin applied.
     *
     * @param enableQuality True to to enable checks, false otherwise.
     **/
    fun enableQuality(enableQuality: Boolean) {
        this.enableQuality = enableQuality
    }

    fun proguard(vararg rules: String = emptyArray()) {
        proguard.applyProguard(rules.asList())
    }

    fun proguard(closure: Closure<FileOutputOptions>) {
        project.configure(proguard, closure)
    }
}
