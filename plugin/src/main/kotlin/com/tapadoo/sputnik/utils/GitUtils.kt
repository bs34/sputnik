package com.tapadoo.sputnik.utils

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author Elliot Tormey
 * @since 07/11/2016
 */
internal class GitUtils {
    fun getCommitHash(): String {
        val command = arrayOf("git", "rev-parse", "--short", "HEAD").execute()
        val hash = command

        if (hash.isEmpty()) {
            return "no_commits"
        } else {
            return hash
        }
    }

    /**
     * This method will search through the git commit history of the project and find any commits matching
     * the grep command below and return the latest pull request number. If none are found it will return
     * the baseValue.
     */
    fun getPullRequestNumber(baseValue: Int): Int {
        val command = arrayOf("sh", "-c", """git --no-pager log --oneline --grep 'pull request #' | head -1""").execute()

        val pr = command.substring(command.indexOf("#") + 1, command.indexOf(")"))
        if (pr.isEmpty()) {
            return baseValue
        } else {
            return Integer.parseInt(pr)
        }
    }

    private fun Array<String>.execute(): String {
        val process = Runtime.getRuntime().exec(this)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val builder = StringBuilder(100)
        var line: String? = reader.readLine()

        while (line != null) {
            builder.append(line)
            builder.append(System.getProperty("line.separator"))
            line = reader.readLine()
        }
        return builder.toString().trim()
    }
}