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

    @Route.File(path = "/script.js")
    fun script(): InputStream?{
        return Resource.get("script.js")
    }

        @Route.File(path = "/recipe.html")
    fun recipeHtml(): InputStream?{
        return Resource.get("recipe.html")
    }

    @Route.File(path = "/recipe.js")
    fun recipeJs(): InputStream?{
        return Resource.get("recipe.js")
    }
}