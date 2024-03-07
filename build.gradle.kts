plugins {
	eclipse
	idea
	application
	kotlin("jvm") version "1.9.22"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("org.mongodb:mongodb-driver-sync:5.0.0")
}

application {
	mainClass = "edu.csus.recipedb.MainKt"
	// passes these system properties from gradle to the JVM for the server
	val args = arrayOf("spoonacular.key", "mongodb.url", "mongodb.username", "mongodb.password")
	val list = mutableListOf<String>()
	for (k in args) {
		val v = System.getProperty(k) ?: continue
		list.add("-D$k=$v")
	}
	applicationDefaultJvmArgs = list
}