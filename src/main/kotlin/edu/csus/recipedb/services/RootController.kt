package edu.csus.recipedb.services

import edu.csus.recipedb.framework.Resource
import edu.csus.recipedb.framework.Route
import edu.csus.recipedb.framework.services.Implementation
import edu.csus.recipedb.framework.services.Service
import java.io.InputStream

@Service.Controller
class RootController: Implementation() {

    @Route.File(path = "/")
    fun root(): InputStream? {
        return Resource.get("index.html")
    }
}