package edu.csus.recipedb.framework.translator

import edu.csus.recipedb.framework.logger.Logger
import java.util.regex.Pattern

class SystemPropertyTranslator: Translator<String, String?> {

    private val logger = Logger.getSystemLogger()
    private val pattern = Pattern.compile("SYS_PROP\\{(.+)}")

    override fun translate(input: String): String? {
        val matcher = pattern.matcher(input)
        if (!matcher.matches())
            return input
        val group = matcher.group(1)
        val property = System.getProperty(group)
        if (property == null)
            logger.error("missing system runtime property: $group")
        return property
    }
}