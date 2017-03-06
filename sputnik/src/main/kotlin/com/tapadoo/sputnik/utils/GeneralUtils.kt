package com.tapadoo.sputnik.utils

import org.gradle.api.Project

/**
 * @author Elliot Tormey
 * @since 19/12/2016
 */
object GeneralUtils {
    fun isLibrary(project: Project): Boolean {
        return project.plugins.hasPlugin("com.android.library")
    }
}