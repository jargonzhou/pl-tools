# maven-publish-git-hash-plugin

A gradle plugin which extend `maven-publish` plugin to append git hash in Maven coordinate.

```
├── README.md
├── build
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── plugin
│   ├── build
│   ├── build.gradle.kts
│   └── src
│       ├── functionalTest
│       │   └── kotlin
│       │       └── com
│       │           └── spike
│       │               └── devops
│       │                   └── GitCoordinatesIntegrationTest.kt      // GradleRunner
│       ├── main
│       │   ├── kotlin
│       │   │   └── com
│       │   │       └── spike
│       │   │           └── devops
│       │   │               ├── GitCoordinatesPlugin.kt               // maven-publish: append git hash in Maven coordinate
│       │   │               └── GitHashValueSource.kt                 // git hash ValueSource
│       │   └── resources
│       └── test
│           ├── kotlin
│           │   └── com
│           │       └── spike
│           │           └── devops
│           │               └── GitCoordinatesPluginTest.kt           // ProjectInternal.evaluate()
│           └── resources
└── settings.gradle.kts
```