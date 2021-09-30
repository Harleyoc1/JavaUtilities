rootProject.name = "JavaUtilities"

val modules = arrayOf("collection", "function", "misc", "reflect", "tuple")

modules.forEach {
    include(it)
    project(":$it").projectDir = file("modules/$it")
}
