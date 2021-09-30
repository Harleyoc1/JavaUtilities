fun getProperty(key: String) = project.findProperty(key).toString()
fun depVersion(name: String) = getProperty("dependency.$name.version")

plugins {
    id("java")
    id("maven-publish")
}

val projectName = getProperty("name")

allprojects {
    group = getProperty("group")
    version = getProperty("version")

    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
        modularity.inferModulePath.set(true)
    }
}

subprojects {
    val moduleName = this@subprojects.findProperty("module.name")

    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(group = "org.jetbrains", name = "annotations", version = depVersion("annotations"))

        testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = depVersion("junit"))
        testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.jar {
        this.archiveBaseName.set("$projectName-$moduleName")
    }

    afterEvaluate {
        tasks.withType<JavaCompile> {
            inputs.property("moduleName", moduleName)
        }
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifactId = "$projectName-$moduleName"

                from(components["java"])

                pom {
                    name.set(projectName)
                    url.set("https://github.com/Harleyoc1/${projectName}")
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://mit-license.org")
                        }
                    }
                    developers {
                        developer {
                            id.set("Harleyoc1")
                            name.set("Harley O'Connor")
                            email.set("harleyoc1@gmail.com")
                        }
                        developer {
                            id.set("ArchieAdams")
                            name.set("Archie Adams")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/Harleyoc1/${projectName}.git")
                        developerConnection.set("scm:git:ssh://github.com/Harleyoc1/${projectName}.git")
                        url.set("https://github.com/Harleyoc1/${projectName}")
                    }
                }
            }
        }
        if (hasProperty("harleyOConnorMavenUsername") && hasProperty("harleyOConnorMavenPassword")) {
            repositories {
                maven {
                    url = uri("https://harleyoconnor.com/maven")
                    credentials {
                        username = getProperty("harleyOConnorMavenUsername")
                        password = getProperty("harleyOConnorMavenPassword")
                    }
                }
            }
        } else {
            logger.log(LogLevel.WARN, "Credentials for maven not detected; it will be disabled.")
        }
    }
}
