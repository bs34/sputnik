package com.tapadoo.sputnik.quality

import com.tapadoo.sputnik.utils.ResourceUtils
import org.gradle.api.plugins.quality.Checkstyle

/**
 * Checkstyle configuration. Used to setup the checkstyle plugin with our rules and suppression
 * and various other settings.
 *
 * @author Elliot Tormey
 * @since 03/12/2016
 */
open class SputnikCheckstyle : Checkstyle() {
    init {
        ResourceUtils.createDirIfNoExist(project, ResourceUtils.CHECKSTYLE_CONFIG_DIR)

        configFile = ResourceUtils.getCheckstyle(project)
        configProperties["checkstyleSuppressionsPath"] = ResourceUtils.getCheckstyleSuppression(project).absolutePath
        configProperties["checkstyleClasspath"] = ResourceUtils.getCheckstyle(project).absolutePath

        ignoreFailures = false
        source("src")
        include("**/*.java")
        include("**/*.kt")
        exclude("**/gen/**")
        classpath = project.files()
    }
}