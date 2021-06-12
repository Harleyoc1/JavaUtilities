# Java Utilities ![](https://img.shields.io/badge/Java-16-green) [![GitHub](https://img.shields.io/github/license/Harleyoc1/JavaUtilities)](./LICENSE)
My custom set of miscellaneous but useful utility classes for various Java projects.

The aims of this project include: 

- Providing features I believe should be included in the JDK. 
- Minimising effort required to use features already included in the JDK.

## Java 16
Versions `0.1.1+` will require Java 16 or higher. Versions below this should work on any version from Java 8 upwards. 

## Gradle Setup
*Don't reinvent the wheel, add it to your build.gradle!*

To add it to your `build.gradle`, first add the following code to the `repositories` section to load my maven repository:

```groovy
maven {
    name 'Harley O\'Connor Maven'
    allowInsecureProtocol = true
    url 'http://harleyoconnor.com/maven/'
}
```

Next, add the following to your `dependencies` section to load java utilities:

```groovy
implementation group: 'com.harleyoconnor.javautilities', name: 'JavaUtilities', version: '0.1.0'
```
