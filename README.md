# Java Utilities
My custom set of miscellaneous but useful utility classes for various Java projects.

## Gradle Setup
*Don't reinvent the wheel, add it to your build.gradle!*

To add it to your `build.gradle`, first add the following code to the `repositories` section to load my maven repository:

```groovy
maven {
    name 'Harley O\'Connor Maven'
    url 'http://harleyoconnor.com/maven/'
}
```

Next, add the following to your `dependencies` section to load java utilities:

```groovy
implementation group: 'com.harleyoconnor.javautilities', name: 'JavaUtilities', version: '0.0.9'
```

Also note that as of Gradle 7 you will need to add the following line to the maven closure from above since my website does not currently have an SSL certificate:

```groovy
allowInsecureProtocol = true
```