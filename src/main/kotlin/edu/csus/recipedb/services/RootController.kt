package edu.csus.recipedb.services

import edu.csus.recipedb.framework.Route
import edu.csus.recipedb.framework.services.Service
import java.io.InputStream

@Service.Controller
class RootController {

    @Route.File(path = "/")
    fun root(): InputStream? {
        return javaClass.getResourceAsStream("/index.html")
    }
}