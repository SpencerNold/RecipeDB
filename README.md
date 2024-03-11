# RecipeDB
 This is a project for CSC131 to practice using the scrum framework implementation of the agile mindset. It is a recipe database using MongoDB and the Spoonacular API.
## Framework Documentation
The framework uses services which can be classified as controllers or database connections. Controllers are for serving data to a web browser, such as html pages, or json data, as well as handling and http requests that may be made by a browser to the server. They are for handling communication between the clients and other layers. Database connections are meant to be used as the layer between the controller and the MongoDB database.
### Using Controllers:
To start using a controller, you need to mark a class with the `@Service.Controller` annotation.
```kotlin
@Service.Controller
class RootController {
}
```
Functions then can be added to the class which are tagged with the `@Route` or `@Route.File` annotations. These routes work as handlers, and accept http requests. For example:
```kotlin
@Route(Http.Method.GET, "/test")
fun test() {
}
```
This route will create a handler that only accepts `GET` requests at the '/test' path. You can test handlers by running the server, and accessing the routes from there. For example, with the current code in the framework, navigating to the website `http://localhost:8080/test` will call this handler. The information returned by the function will be served back to the web browser in the json format. As this function has no return type and returns `Unit`, the http response code `204` will be sent. Supported methods include `GET`, `POST`, `PUT`, and `DELETE`.
### Frontend:
To work with the framework for the frontend, you need to add resources to the resources folder. This can be found in the resources subfolder in the `src` folder. Adding pages to the web server can be done by adding a file, `index.html` for example, to the `src/resources` folder, then adding a corresponding route.

```kotlin
@Route.File("/")
fun root(): InputStream? {
    return Resource.get("index.html")
}
```
With this route added to the controller, accessing `http://localhost:8080/` will result in the html page being displayed. Routes tagged with the `@Route.File` annotation can only return types `InputStream`, `InputStream?`, `File`, and `File?`.
### Database:

### Spoonacular: