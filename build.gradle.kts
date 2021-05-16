fun property(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("maven-publish")
}

val projectName = property("name")
group = property("group")
version = property("version")

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "com.google.code.findbugs", name = "jsr305", version = property("findBugsVersion"))

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = property("junitVersion"))
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = projectName

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
                        id.set("harleyoconnor")
                        name.set("Harley O\"Connor")
                        email.set("harleyoc1@gmail.com")
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
}