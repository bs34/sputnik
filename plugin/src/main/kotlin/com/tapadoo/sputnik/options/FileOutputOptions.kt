package com.tapadoo.sputnik.options

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.tapadoo.sputnik.utils.GitUtils
import groovy.text.SimpleTemplateEngine
import org.gradle.api.Project

/**
 * Used to configure how the file name of the apk for the project is generated. User can specify the format
 * using the predefined keys below (e.g. appName, projectName...) and can include static text
 * (e.g. '_', '-', ':')
 *
 * <pre>
 * sputnik {
 *   apkName {
 *     nameFormat '${appName}-${versionName}'
 *   }
 * }
 * </pre>
 *
 * @author Elliot Tormey
 * @since 28/10/2016
 */
class FileOutputOptions(val project: Project) {
    private val android by lazy { project.extensions.getByName("android") as AppExtension }
    private val templateEngine by lazy { SimpleTemplateEngine() }

    var appName = project.name
    var nameFormat = ""

    fun appName(appName: String) {
        this.appName = appName
    }

    fun nameFormat(nameFormat: String) {
        this.nameFormat = nameFormat
    }

    fun generateOutputName() {
        // Only need to change the apk name if its an Android application project, not library project.
        if (project.pluginManager.hasPlugin("com.android.application")) {
            // Only call this once the project has been evaluated to make sure
            // that all relevant information is available.
            project.afterEvaluate {
                android.applicationVariants.all {
                    generateOutputName(it)
                }
            }
        }
    }

    private fun generateOutputName(variant: ApplicationVariant) {
        val gitUtils = GitUtils()
        val map = mapOf<String, String>(
                "appName" to appName.toLowerCase(),
                "projectName" to project.rootProject.name,
                "flavorName" to variant.flavorName,
                "buildType" to variant.buildType.name,
                "versionName" to variant.versionName,
                "versionCode" to variant.versionCode.toString(),
                "commitHash" to gitUtils.getCommitHash()
        )

        val template: String
        if (nameFormat.isEmpty()) {
            if (variant.flavorName != "" && variant.flavorName != null) {
                template = "\$appName-\$versionName-\$flavorName-\$buildType"
            } else {
                template = "\$appName-\$versionName-\$buildType"
            }
        } else {
            template = nameFormat
        }

        val fileName = templateEngine.createTemplate(template).make(map).toString()
        project.setProperty("archivesBaseName", fileName)
    }
}