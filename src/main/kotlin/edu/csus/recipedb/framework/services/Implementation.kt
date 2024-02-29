package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.WebServer

abstract class Implementation {

    private var server: WebServer? = null

    protected fun <T> getService(clazz: Class<T>): T {
        return server!!.service(clazz)!!
    }
}