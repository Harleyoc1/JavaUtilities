rootProject.name = "JavaUtilities"

val modules = arrayOf("base", "collection", "function", "reflect", "tuple")

modules.forEach {
    include(it)
    project(":$it").projectDir = file("modules/$it")
}
