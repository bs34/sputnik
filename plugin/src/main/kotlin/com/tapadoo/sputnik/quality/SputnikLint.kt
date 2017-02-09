package com.tapadoo.sputnik.quality

import com.android.build.gradle.BaseExtension
import com.tapadoo.sputnik.utils.ResourceUtils
import org.gradle.api.Project

/**
 * LintOptions configuration. Used to setup lint with our rules and various other settings.
 *
 * @author Elliot Tormey
 * @since 03/12/2016
 */
open class SputnikLint(project: Project,
                       android: BaseExtension) {

    init {
        android.lintOptions.apply {
            ResourceUtils.createDirIfNoExist(project, ResourceUtils.LINT_CONFIG_DIR)

            isAbortOnError = true
            xmlReport = false
            htmlReport = true
            lintConfig = ResourceUtils.getLint(project)
            htmlOutput = project.file("${project.buildDir}/reports/lint/lint-result.html")
            xmlOutput = project.file("${project.buildDir}/reports/lint/lint-result.xml")
        }
    }
}