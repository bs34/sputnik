package com.tapadoo.sputnik

import com.android.build.gradle.BaseExtension
import com.tapadoo.sputnik.quality.SputnikCheckstyle
import com.tapadoo.sputnik.quality.SputnikFindBugs
import com.tapadoo.sputnik.quality.SputnikLint
import com.tapadoo.sputnik.quality.SputnikPmd
import com.tapadoo.sputnik.tasks.ProguardTask
import com.tapadoo.sputnik.utils.ResourceUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension

class SputnikPlugin : Plugin<Project> {
    private val SPUTNIK = "sputnik"
    private val CHECK = "check"
    private val PRE_BUILD = "preBuild"

    override fun apply(project: Project) {
        val android = project.extensions.getByName("android") as BaseExtension
        val sputnik = project.extensions.create(SPUTNIK, SputnikPluginExtension::class.java, project)
        project.tasks.create("listProguard", ProguardTask::class.java)

        sputnik.fileOptions.generateOutputName()

        // Code quality checks are enabled by default. To disable them, set the {@code enableQuality}
        // flag to false.
        project.afterEvaluate {
            if (sputnik.enableQuality) {
                // Lint
                SputnikLint(project, android)

                // Checkstyle
                project.plugins.apply(ResourceUtils.CHECKSTYLE)
                val checkstyle = project.tasks.create(ResourceUtils.CHECKSTYLE, SputnikCheckstyle::class.java)
                project.extensions.getByType(CheckstyleExtension::class.java).toolVersion = "7.3"

                // FindBugs
                project.plugins.apply(ResourceUtils.FINDBUGS)
                project.tasks.create(ResourceUtils.FINDBUGS, SputnikFindBugs::class.java)

                // PMD
                project.plugins.apply(ResourceUtils.PMD)
                project.tasks.create(ResourceUtils.PMD, SputnikPmd::class.java)

                project.tasks.getByName(PRE_BUILD).dependsOn(checkstyle)
                project.tasks.getByName(CHECK).dependsOn(ResourceUtils.FINDBUGS, ResourceUtils.PMD, ResourceUtils.LINT)
            }
            sputnik.proguard()
        }
    }
}