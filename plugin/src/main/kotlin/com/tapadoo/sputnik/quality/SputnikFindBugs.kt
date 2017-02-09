package com.tapadoo.sputnik.quality

import com.tapadoo.sputnik.utils.ResourceUtils
import org.gradle.api.plugins.quality.FindBugs

/**
 * FindBugs configuration. Used to setup the findbugs plugin with our filter and various other settings.
 *
 * @author Elliot Tormey
 * @since 03/12/2016
 */
open class SputnikFindBugs : FindBugs() {
    init {
        ResourceUtils.createDirIfNoExist(project, ResourceUtils.FINDBUGS_CONFIG_DIR)

        ignoreFailures = false
        effort = "max"
        reportLevel = "high"
        excludeFilter = ResourceUtils.getFindBugsFilter(project)
        classes = project.files("${project.buildDir}/intermediates/classes")
        source("src")
        include("**/*.java")
        include("**/*.kt")
        exclude("**/gen/**")

        reports.apply {
            xml.isEnabled = false
            html.isEnabled = true
            xml.setDestination("${project.buildDir}/reports/findbugs/findbugs.xml")
            html.setDestination("${project.buildDir}/reports/findbugs/findbugs.html")
        }
        classpath = project.files()
    }
}