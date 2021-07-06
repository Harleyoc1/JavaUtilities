# Java Utilities ![](https://img.shields.io/badge/Java-16-green) [![GitHub](https://img.shields.io/github/license/Harleyoc1/JavaUtilities)](./LICENSE) ![](https://img.shields.io/github/workflow/status/Harleyoc1/JavaUtilities/Java%20CI%20with%20Gradle) [![](https://img.shields.io/github/v/tag/Harleyoc1/JavaUtilities)](https://github.com/Harleyoc1/JavaUtilities/releases)
My custom set of miscellaneous but useful utility classes for various Java projects.

The aims of this project include: 

- Providing features I believe should be included in the JDK. 
- Minimising effort required to use features already included in the JDK.

There is no documentation, however all public and protected classes, methods, and fields have Javadoc. 

## Java 16
Versions `0.1.1+` will require Java 16 or higher. Versions below this should work on any version from Java 8 upwards. 

## Gradle Setup
*Don't reinvent the wheel, add it to your build.gradle!*

To add it to your `build.gradle`, first add the following code to the `repositories` section to load my maven repository:

`build.gradle` [for Groovy build scripts]

```groovy
maven {
    name 'Harley O\'Connor Maven'
    url 'https://harleyoconnor.com/maven/'
}
```

`build.gradle.kts` [for Kotlin build scripts]
```kotlin
maven("https://harleyoconnor.com/maven/")
```

Next, add the following to your `dependencies` section to load java utilities:

`build.gradle` [for Groovy build scripts]
```groovy
implementation group: 'com.harleyoconnor.javautilities', name: 'JavaUtilities', version: '0.1.1'
```

`build.gradle.kts` [for Kotlin build scripts]
```kotlin
implementation(group = "com.harleyoconnor.javautilities", name = "JavaUtilities", version = "0.1.1")
```
