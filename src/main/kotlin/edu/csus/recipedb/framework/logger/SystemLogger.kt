package edu.csus.recipedb.framework.logger

import java.text.SimpleDateFormat
import java.util.*

class SystemLogger: Logger() {

    override fun log(severity: Severity, message: String) {
        val print = "[${severity.name}: ${getDateTime()}] $message"
        if (severity == Severity.ERROR) {
            System.err.println(print)
        } else {
            println(print)
        }
    }

    private fun getDateTime(): String {
        return SimpleDateFormat("yyy/MM/dd HH:mm:ss").format(Calendar.getInstance().time)
    }
}