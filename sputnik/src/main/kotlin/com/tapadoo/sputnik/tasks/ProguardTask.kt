package com.tapadoo.sputnik.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Gradle task to print out all available proguard configuration files packaged with Sputnik.
 *
 * @author Elliot Tormey
 * @since 27/12/2016
 */
open class ProguardTask : DefaultTask() {
    private val availableRules =
                    "    - default\n" +
                    "    - android\n" +
                    "    - android-optimized\n" +
                    "    - project\n" +
                    "    - library\n" +
                    "    - android-v7\n" +
                    "    - android-design\n" +
                    "    - crashlytics\n" +
                    "    - firebase\n" +
                    "    - gson\n" +
                    "    - realm\n" +
                    "    - retrofit-2\n" +
                    "    - rxJava"

    @TaskAction
    fun listProguard() {
        println(availableRules)
    }
}