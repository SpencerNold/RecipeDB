package edu.csus.recipedb.services

import edu.csus.recipedb.framework.database.Driver
import edu.csus.recipedb.framework.database.MongoDatabase
import edu.csus.recipedb.framework.services.Service

@Service.Database(driver = Driver.Type.MONGO, url = "SYS_PROP{mongodb.url}", username = "SYS_PROP{mongodb.username}", password = "SYS_PROP{mongodb.password}")
class DatabaseLayer: MongoDatabase() {

    fun test() {
        // execute("db.Favoritied_Recipies.find( { \"RecipeID\": \"565545\" } )")
    }
}