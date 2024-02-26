package edu.csus.recipedb.framework.logger

import java.text.SimpleDateFormat
import java.util.*

class SystemLogger: Logger() {

    override fun log(severity: Severity, message: String) {
        println("[${severity.name}: ${getDateTime()}] $message")
    }

    private fun getDateTime(): String {
        return SimpleDateFormat("yyy/MM/dd HH:mm:ss").format(Calendar.getInstance().time)
    }
}