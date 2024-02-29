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
}

application {
	mainClass = "edu.csus.recipedb.MainKt"
	val args = arrayOf("mongodb.url", "mongodb.username", "mongodb.password")
	val list = mutableListOf<String>()
	for (k in args) {
		val v = System.getProperty(k) ?: continue
		list.add("-D$k=$v")
	}
	applicationDefaultJvmArgs = list
}