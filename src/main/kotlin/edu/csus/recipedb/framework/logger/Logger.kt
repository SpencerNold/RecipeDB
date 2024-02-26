package edu.csus.recipedb.framework.logger

abstract class Logger {

    companion object {
        fun getSystemLogger(): Logger {
            return SystemLogger()
        }
    }

    fun info(message: String) {
        log(Severity.INFO, message)
    }

    fun warn(message: String) {
        log(Severity.WARN, message)
    }

    fun error(message: String) {
        log(Severity.ERROR, message)
    }

    abstract fun log(severity: Severity, message: String)

    enum class Severity {
        INFO, WARN, ERROR
    }
}