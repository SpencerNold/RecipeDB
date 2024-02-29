package edu.csus.recipedb.services

import edu.csus.recipedb.framework.database.Database
import edu.csus.recipedb.framework.database.Driver
import edu.csus.recipedb.framework.services.Service

@Service.Database(driver = Driver.Type.MONGO, url = "SYS_PROP{mongodb.url}", username = "SYS_PROP{mongodb.username}", password = "SYS_PROP{mongodb.password}")
class MongoDatabase: Database() {
}