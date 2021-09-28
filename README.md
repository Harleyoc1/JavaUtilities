# Java Utilities ![](https://img.shields.io/badge/Java-16-green) [![GitHub](https://img.shields.io/github/license/Harleyoc1/JavaUtilities)](./LICENSE) ![](https://img.shields.io/github/workflow/status/Harleyoc1/JavaUtilities/Java%20CI%20with%20Gradle) [![](https://img.shields.io/github/v/tag/Harleyoc1/JavaUtilities)](https://github.com/Harleyoc1/JavaUtilities/releases)
My custom set of miscellaneous but useful utility classes for various Java projects.

The aims of this project include:

- Providing features I believe should be included in the JDK.
- Minimising effort required to use features already included in the JDK.

There is no documentation, however all public and protected classes, methods, and fields have Javadoc.

## Java 16

Versions `0.1.1+` will require Java 16 or higher. Versions below this should work on any version from Java 8 upwards.

## Modules

As of version `0.2.0`, Java Utilities is modular. Each respective module is also a Java module, published under a
separate Jar; this allows you to selectively add the modules you require.

- `collection` - `javautilities.collection`
- `function` - `javautilities.function`
- `misc` - `javautilities.misc`
- `reflect` - `javautilities.reflect`
- `tuple` - `javautilities.tuple`

Do however note that some modules require others to function. These should be automatically detected and added as
runtime dependencies by Gradle.

## Gradle Setup

*Don't reinvent the wheel, add it to your build.gradle!*

To add it to your `build.gradle`, first add the following code to the `repositories` section to load my maven
repository:

#### Groovy | `build.gradle`

```groovy
maven {
    name 'Harley O\'Connor Maven'
    url 'https://harleyoconnor.com/maven/'
}
```

#### Kotlin | `build.gradle.kts`
```kotlin
maven("https://harleyoconnor.com/maven/")
```

Next, add the following to your `dependencies` section to load Java Utilities. You will need to add this line for
each [module](#modules) you want, replacing `<module>` with the module name.

#### Groovy | `build.gradle`
```groovy
implementation group: 'com.harleyoconnor.javautilities', name: 'JavaUtilities-<module>', version: '0.2.0'
```

#### Kotlin | `build.gradle.kts`
```kotlin
implementation(group = "com.harleyoconnor.javautilities", name = "JavaUtilities-<module>", version = "0.2.0")
```
