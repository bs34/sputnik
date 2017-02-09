package com.tapadoo.sputnik.utils

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * Helper class used to load files from the '/resources' directory in Sputnik into a project.
 *
 * @author Elliot Tormey
 * @since 22/12/2016
 */
internal object ResourceUtils {
    const val CONFIG_DIR = "config"

    private const val PROGUARD_DIR = "proguard"
    const val PROGUARD_CONFIG_DIR = "$CONFIG_DIR/$PROGUARD_DIR"
    private const val PROGUARD_DEFAULT = "proguard-sputnik-default.pro"
    private const val PROGUARD_ANDROID = "proguard-android.txt"
    private const val PROGUARD_ANDROID_OPTIMIZED = "proguard-android-optimize.txt"
    private const val PROGUARD_APPLICATION = "proguard-rules.pro"
    private const val PROGUARD_LIBRARY = "proguard-library.pro"
    private const val PROGUARD_ANDROID_7 = "proguard-android-v7.pro"
    private const val PROGUARD_ANDROID_DESIGN = "proguard-android-design.pro"
    private const val PROGUARD_CRASHLYTICS = "proguard-crashlytics.pro"
    private const val PROGUARD_FIREBASE = "proguard-firebase.pro"
    private const val PROGUARD_GSON = "proguard-gson.pro"
    private const val PROGUARD_REALM = "proguard-realm.pro"
    private const val PROGUARD_RETROFIT_2 = "proguard-retrofit-2.pro"
    private const val PROGUARD_RXJAVA = "proguard-rxjava.pro"

    private const val QUALITY_DIR = "quality"

    const val CHECKSTYLE = "checkstyle"
    const val CHECKSTYLE_CONFIG_DIR = "$CONFIG_DIR/$QUALITY_DIR/$CHECKSTYLE"
    private const val CHECKSTYLE_DIR = "$QUALITY_DIR/$CHECKSTYLE"
    private const val CHECKSTYLE_FILE = "checkstyle.xml"
    private const val SUPPRESSION_FILE = "suppressions.xml"

    const val FINDBUGS = "findbugs"
    const val FINDBUGS_CONFIG_DIR = "$CONFIG_DIR/$QUALITY_DIR/$FINDBUGS"
    private const val FINDBUGS_DIR = "$QUALITY_DIR/$FINDBUGS"
    private const val FINDBUGS_FILE = "findbugs-filter.xml"

    const val LINT = "lint"
    const val LINT_CONFIG_DIR = "$CONFIG_DIR/$QUALITY_DIR/$LINT"
    private const val LINT_DIR = "$QUALITY_DIR/$LINT"
    const val LINT_FILE = "lint.xml"

    const val PMD = "pmd"
    const val PMD_CONFIG_DIR = "$CONFIG_DIR/$QUALITY_DIR/$PMD"
    private const val PMD_DIR = "$QUALITY_DIR/$PMD"
    private const val PMD_FILE = "pmd-ruleset.xml"

    /**
     * Creates a file in the projects `/config` directory with the given parameters.
     *
     * @param project The project to write the files to.
     * @param directoryName The name of the directory to save the files in.
     * @param fileName The name of the dile to save.
     */
    private fun createFileFromResource(project: Project, directoryName: String, fileName: String): File {
        val destination: Path = Paths.get("${project.rootDir}/$CONFIG_DIR/$directoryName/$fileName")
        ResourceUtils::class.java.getResourceAsStream("/$directoryName/$fileName").use {
            `in`: InputStream ->
            Files.copy(`in`, destination, StandardCopyOption.REPLACE_EXISTING)
        }

        return destination.toFile()
    }

    /**
     * Creates a directory with the given name in the projects root directory if one does not
     * already exist.
     *
     * @param project The project to create the directory in.
     * @param directoryName The name to set the directory as.
     */
    fun createDirIfNoExist(project: Project, directoryName: String) {
        val directory = File("${project.rootDir}/$directoryName")
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    fun getDefaultProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_DEFAULT)
    }

    fun getAndroidProguard(android: BaseExtension): File {
        return android.getDefaultProguardFile(PROGUARD_ANDROID)
    }

    fun getAndroidOptimizedProguard(android: BaseExtension): File {
        return android.getDefaultProguardFile(PROGUARD_ANDROID_OPTIMIZED)
    }

    fun getApplicationProguard(project: Project): File {
        return Paths.get("${project.rootDir}/app/$PROGUARD_APPLICATION").toFile()
    }

    fun getLibraryProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_LIBRARY)
    }

    fun getAndroidV7Proguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_ANDROID_7)
    }

    fun getAndroidDesignProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_ANDROID_DESIGN)
    }

    fun getCrashlyticsProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_CRASHLYTICS)
    }

    fun getFirebaseProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_FIREBASE)
    }

    fun getGsonProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_GSON)
    }

    fun getRealmProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_REALM)
    }

    fun getRetrofit2Proguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_RETROFIT_2)
    }

    fun getRxJavaProguard(project: Project): File {
        return createFileFromResource(project, PROGUARD_DIR, PROGUARD_RXJAVA)
    }

    fun getCheckstyle(project: Project): File {
        return createFileFromResource(project, CHECKSTYLE_DIR, CHECKSTYLE_FILE)
    }

    fun getCheckstyleSuppression(project: Project): File {
        return createFileFromResource(project, CHECKSTYLE_DIR, SUPPRESSION_FILE)
    }

    fun getFindBugsFilter(project: Project): File {
        return createFileFromResource(project, FINDBUGS_DIR, FINDBUGS_FILE)
    }

    fun getLint(project: Project): File {
        return createFileFromResource(project, LINT_DIR, LINT_FILE)
    }

    fun getPmd(project: Project): File {
        return createFileFromResource(project, PMD_DIR, PMD_FILE)
    }
}