package com.tapadoo.sputnik.quality

import com.tapadoo.sputnik.utils.ResourceUtils
import org.gradle.api.plugins.quality.Pmd

/**
 * Pmd configuration. Used to setup the pmd plugin with our rules and various other settings.
 *
 * @author Elliot Tormey
 * @since 03/12/2016
 */
open class SputnikPmd : Pmd() {
    init {
        ResourceUtils.createDirIfNoExist(project, ResourceUtils.PMD_CONFIG_DIR)

        ignoreFailures = false
        ruleSetFiles = project.files(ResourceUtils.getPmd(project))
        ruleSets = emptyList()
        source("src")
        include("**/*.java")
        include("**/*.kt")
        exclude("**/gen/**")

        reports.apply {
            xml.isEnabled = false
            html.isEnabled = true
            xml.setDestination("${project.buildDir}/reports/pmd/pmd.xml")
            html.setDestination("${project.buildDir}/reports/pmd/pmd.html")
        }
    }
}