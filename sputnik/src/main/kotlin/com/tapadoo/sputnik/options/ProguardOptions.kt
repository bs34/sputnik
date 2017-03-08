package com.tapadoo.sputnik.options

import com.android.build.gradle.BaseExtension
import com.tapadoo.sputnik.extensions.filterNotWith
import com.tapadoo.sputnik.utils.GeneralUtils
import com.tapadoo.sputnik.utils.ResourceUtils
import org.gradle.api.Project
import java.io.File

/**
 * Class that will apply the given proguard files to a project.
 *
 * @author Elliot Tormey
 * @since 18/12/2016
 */
class ProguardOptions(private val project: Project,
                      private val android: BaseExtension) {
    var include: MutableList<String> = mutableListOf("default")
    var exclude: List<String> = emptyList()

    init {
        ResourceUtils.createDirIfNoExist(project, ResourceUtils.PROGUARD_CONFIG_DIR)
    }

    // The available proguard rules that can be used.
    private val rulesMap = mapOf<String, File>(
            "default" to ResourceUtils.getDefaultProguard(project),
            "android" to ResourceUtils.getAndroidProguard(android),
            "android-optimized" to ResourceUtils.getAndroidOptimizedProguard(android),
            "project" to ResourceUtils.getApplicationProguard(project),
            "library" to ResourceUtils.getLibraryProguard(project),
            "android-v7" to ResourceUtils.getAndroidV7Proguard(project),
            "android-design" to ResourceUtils.getAndroidDesignProguard(project),
            "crashlytics" to ResourceUtils.getCrashlyticsProguard(project),
            "firebase" to ResourceUtils.getFirebaseProguard(project),
            "gson" to ResourceUtils.getGsonProguard(project),
            "realm" to ResourceUtils.getRealmProguard(project),
            "retrofit-2" to ResourceUtils.getRetrofit2Proguard(project),
            "rxJava" to ResourceUtils.getRxJavaProguard(project)
    )

    /**
     * Applies proguard files to the calling project. If its library project they will be applied
     * as `consumerProguardFiles`.
     *
     * @param rules A List of keys associated with proguard files supplied by Sputnik.
     */
    fun applyProguard(rules: List<String> = emptyList()) {
        // Merge the supplied list and the include list together.
        val proguardList: MutableList<String> = rules.toMutableList()
        proguardList.addAll(include)

        // If it a library project, add the library proguard, else add the regular android and project proguard.
        if (GeneralUtils.isLibrary(project)) {
            proguardList.add("library")
        } else {
            proguardList.addAll(listOf("android", "project"))
        }

        // Filter out the excluded rules.
        proguardList.filterNotWith(exclude)

        proguardList.forEach {
            if (GeneralUtils.isLibrary(project)) {
                // If the project is a library, set the proguard files as `consumerProguardFiles`
                android.defaultConfig.consumerProguardFiles.add(rulesMap[it])
            } else {
                android.defaultConfig.proguardFiles.add(rulesMap[it])
            }
        }
    }

    /**
     * Call this function to include a proguard file to be added to the project. Can be either a
     * single item or an Array.
     *
     * @param include The proguard file/files to be added.
     */
    fun include(vararg include: String) {
        this.include.addAll(include)
    }

    /**
     * Call this function to include a proguard file to be excluded to the project. Can be either a
     * single item or an Array.
     *
     * @param exclude The proguard file/files to be excluded.
     */
    fun exclude(vararg exclude: String) {
        // TODO : Elliot -> If proguard file is already applied to the project, delete it.
        this.exclude = exclude.asList()
    }
}