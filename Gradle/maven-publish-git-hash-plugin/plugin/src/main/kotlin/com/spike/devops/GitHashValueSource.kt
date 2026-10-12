package com.spike.devops

import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

abstract class GitHashValueSource : ValueSource<String, ValueSourceParameters.None> {
    @get:Inject
    abstract val execOperations: ExecOperations

    override fun obtain(): String {
        return try {
            val outputStream = ByteArrayOutputStream()
            execOperations.exec {spec ->
                spec.commandLine("git", "rev-parse", "--short", "HEAD")
                spec.standardOutput = outputStream
                spec.isIgnoreExitValue = true
            }
            outputStream.toString().trim()
        } catch (e: Exception) {
            "unknown"
        }
    }
}