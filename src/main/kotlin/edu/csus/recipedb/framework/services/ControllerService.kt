package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.Route
import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.framework.handlers.GenericHttpHandler
import edu.csus.recipedb.framework.handlers.StaticFileHttpHandler

class ControllerService(clazz: Class<*>, private val controller: Controller): Service(Type.CONTROLLER, clazz) {

    override fun start(server: WebServer): Any? {
        val instance = try {
            clazz.getDeclaredConstructor().newInstance()
        } catch (e: NoSuchMethodException) {
            logger.error("Unable to start controller of class: ${clazz.name}")
            return null
        }
        implement(instance, server)
        for (method in clazz.declaredMethods) {
            if (method.isAnnotationPresent(Route::class.java)) {
                val route = method.getAnnotation(Route::class.java)
                val path = controller.path + route.path
                server.addHandler(path, GenericHttpHandler(route, instance, method))
            } else if (method.isAnnotationPresent(Route.File::class.java)) {
                val path = controller.path + method.getAnnotation(Route.File::class.java).path
                server.addHandler(path, StaticFileHttpHandler.getHandler(instance, method))
            } else if (method.isAnnotationPresent(Route.Directory::class.java)) {
                TODO("Not implemented yet!")
            }
        }
        return instance
    }
}