# Java Utilities
My custom set of miscellaneous but useful utility classes for various Java projects.

## Gradle Setup
To add it to your build.gradle, first add the following code to the `repositories` section to load my maven repository:

```
maven {
    name 'Harley O\'Connor Maven'
    url 'http://harleyoconnor.com/maven/'
}
```

Next, add the following to your `dependencies` section to load java utilities:

```
implementation group: 'com.harleyoconnor.javautilities', name: 'JavaUtilities', version: '0.0.7'
```