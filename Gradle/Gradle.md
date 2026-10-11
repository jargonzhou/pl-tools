# Gradle
* https://gradle.org/
* https://github.com/gradle/gradle

> Gradle is a highly scalable build automation tool designed to handle everything from large, multi-project enterprise builds to quick development tasks across various languages. Gradle’s modular, performance-oriented architecture seamlessly integrates with development environments, making it a go-to solution for building, testing, and deploying applications on Java, Kotlin, Scala, Android, Groovy, C++, and Swift.

books
* Benjamin Muschko. **Gradle in Action**. Manning Publications: 2014.

UML
* [gradle.uxf](./gradle.uxf)

# Gradle Fundamentals

Learning Gradle Basics
1. Core Concepts
2. Wrapper Basics
3. CLI Basics
4. Settings File Basics `settings.gradle(.kts)`
5. Build File Basics `build.gradle(.kts)`
6. Dependencies Basics `dependencies {}`
   1. `gradle/libs.versions.toml` `libs`
7. Tasks Basics
8. Caching Basics
   1. task outcome labels: `gradle.properties org.gradle.console=verbose`, `--console=verbose`
   2. incremental builds
   3. build caching: `--build-cache`
10. Plugins Basics: `plugins {}`
    1.  core plugins
    2.  community plugins: portal https://plugins.gradle.org/
    3.  custom/local plugins
11. Build Scan Basics: `--scan`
    1.  build scan service: https://scans.gradle.com/

![](https://docs.gradle.org/9.8.1/userguide/img/gradle-basic-1.png)

Writing Build Scripts
1. Anatomy of a Gradle Build
   1. project root directory, `GRADLE_USE_HOME ~/.gradle`
2. Structuring Multi-Project Builds
   1. `settings.gradle.kts include(...)`
   2. `buildSrc`: multi-project builds
   3. `build-logic`: composite builds, `includeBuild("build-logic")`
3. Gradle Build Lifecycle
   1. initialization: `settings.gradle.kts` `Settings`, `Project`
   2. configuration: `build.gradle.kts`
   3. execution
   4. lifecycle API: callbacks, listeners
4. Writing Build Scripts
   1. script structure: statements, blocks (Kotlin lambdas with receivers)
5. Gradle Managed Types
   1. `Property`, `Provider`
6. Declaring Dependencies
   1. `dependencies{}` bucket configurations
7. Creating and Registering Tasks
   1. `tasks`
8. Working with Plugins
   1. `plugins {}`

Creating Plugins
1. Plugin Introduction
   1. Init plugin: `~/.gradle/init.gradle[.kts]`, `--init-script` - `Plugin<Gradle>` global scope
   2. Settings plugin: `settings.gradle[.kts]` - `Plugin<Settings>` build scope
   3. Project plugin: `build.gradle[.kts]` - `Plugin<Project>` project scope
   4. sources: core, community, local
      1. custom: precompiled scripts plugins(`.gradle[.kts]`), binary plugins
2. Pre-Compiled Script Plugins
   1. convention plugins
   2. ex: `buildSrc/src/main/kotlin/java-library-convention.gradle.kts`
3. Binary Plugins
4. Developing Binary Plugins
5. Testing Binary Plugins
6. Publishing Binary Plugins

# Gradle Tutorials

Beginner Tutorial
1. Initializing the Project
2. Running Tasks
3. Understanding Dependencies
4. Applying Plugins
5. Exploring Incremental Builds
6. Enabling the Build Cache

Intermediate Tutorial
1. Initializing the Project
2. Understanding the Build Lifecycle
3. Multi-Project Builds
4. Writing the Settings File
5. Writing a Build Script
6. Writing Tasks
7. Writing Plugins

Advanced Tutorial
1. Initializing the Project
2. Adding an Extension
3. Creating a Custom Task
4. Writing a Unit Test
5. Adding a DataFlow Action
6. Writing a Functional Test
7. Using a Consumer Project
8. Publish the Plugin

# Gradle Best Practices

Introduction

Best Practices
- Best Practices Index
- General Best Practices
- Best Practices for Structuring Builds
- Best Practices for Dependencies
- Best Practices for Tasks
- Best Practices for Performance
- Best Practices for Security
- Best Practices for Testing

# Gradle DSLs and APIs
Groovy
- Groovy DSL Primer
- Groovy DSL Reference

Kotlin
- Kotlin DSL Primer
- Kotlin DSL Reference
- Migration from Groovy

General
- Java API
- Public APIs
- Default Script Imports/默认脚本中导入

# Gradle Reference

## Runtime and Configuration
- Command-Line Interface
- Logging and Output
- Gradle Wrapper
- Gradle Daemon
- Gradle Directories
- Build Configuration
- Build Lifecycle
- Build Scan
- Continuous Builds
- File System Watching

## Core Plugins
Core Plugins

JVM Plugins
- Java Plugin
- Java Library Plugin
- Java Platform Plugin
- Groovy Plugin
- Scala Plugin
- ANTLR Plugin
- JVM Test Suite Plugin
- Java Test Fixtures Plugin
- Test Report Aggregation Plugin
- JVM Toolchains Plugins

Native Plugins
- C++ Application Plugin
- C++ Library Plugin
- C++ Unit Test Plugin
- Swift Application Plugin
- Swift Library Plugin
- XCTest Plugin

Packaging/Distribution Plugins
- Java Application Plugin
- WAR Plugin
- EAR Plugin
- Maven Publish Plugin
- Ivy Publish Plugin
- Distribution Plugin
- Java Library Distribution Plugin
- Version Catalog Plugin

Code Analysis Plugins
- Checkstyle Plugin
- PMD Plugin
- JaCoCo Plugin
- JaCoCo Report Aggregation Plugin
- CodeNarc Plugin

IDE Integration Plugins
- Eclipse Plugin
- IntelliJ IDEA Plugin
- Visual Studio Plugin
- Xcode Plugin

Utility Plugins
- Base Plugin
- Build Init Plugin
- Signing Plugin
- Java Gradle Plugin
- Groovy Gradle Plugin
- Project Report Plugin
- Build Dashboard Plugin

## Tasks
- Understanding Tasks
- Controlling Task Execution
- Organizing Tasks
- Implementing Custom Tasks
- Lazy Configuration
- Parallel Task Execution
- Advanced Task Development
- Shared Build Services
- Task Configuration Avoidance

## Plugins
- Introduction to Plugins
- Precompiled Script Plugins
- Convention Plugins
- Binary Plugins
- Testing Plugins
- Preparing to Publish
- Publishing Plugins
- Reporting Plugin Problems
- Initialization Scripts & Init Plugins
- Testing with TestKit

## Dependencies
Getting Started

Learning the Basics
1. Declaring Dependencies
2. Dependency Configurations
3. Declaring Repositories
4. Centralizing Dependencies
5. Dependency Constraints and Conflict Resolution

Declaring Dependencies
- Declaring Dependencies
- Viewing Dependencies
- Declaring Versions and Ranges
- Declaring Dependency Constraints
- Creating Dependency Configurations
- Gradle Distribution-Specific Dependencies

Declaring Repositories
- Declaring Repositories
- Centralizing Repository Declarations
- Repository Types
- Metadata Formats
- Supported Protocols
- Filtering Repository Content

Centralizing Dependencies
- Platforms (BOMs)
- Version Catalogs
- Using Catalogs with Platforms

Controlling Dependency Resolution
- Consistent Dependency Resolution
- Resolving Specific Artifacts
- Capabilities
- Variants and Attributes
- Artifact Views
- Artifact Transforms

Managing Dependencies
- Locking Versions
- Using Resolution Rules
- Modifying Dependency Metadata
- Caching Dependencies

Advanced Concepts
1. Dependency Resolution
2. Graph Resolution
3. Variant Selection
4. Artifact Resolution

Publishing Libraries
- Setting up Publishing
- Understanding Gradle Module Metadata
- Signing Artifacts
- Customizing Publishing

## Gradle Managed Types
- Lazy vs Eager Evaluation
- Properties and Providers
- Collections
- Services and Service Injection
- Dataflow Actions
- Working with Files

## Platforms
JVM Builds
- Building Java & JVM projects
- Testing Java & JVM projects
- Java Toolchains
  - Toolchains for JVM projects
  - Toolchain Resolver Plugins
- Managing Dependencies

C++ Builds
- Building C++ projects
- Testing C++ projects
- Building Native Software

Swift Builds
- Building Swift projects
- Testing Swift projects

## Other Topics
- Using Ant from Gradle

# Gradle on CI
Introduction

CI/CD Systems
- GitHub Actions
- GitLab CI
- Jenkins
- TeamCity
- Travis CI

# Structuring Gradle Builds
Organizing Projects

Multi-Project Builds

Sharing Build Logic

Composite Builds

Configuration on Demand

# Optimizing Gradle Builds
Improving Performance

Build Cache
- Enabling and Configuring
- Why use the Build Cache?
- Understanding the Impact
- Learning Basic Concepts
- Caching Java Project
- Caching Android Project
- Debugging Caching Issues
- Troubleshooting

Configuration Cache
- How it Works
- Enabling and Configuring
- Adopting Step by Step
- Debugging and Troubleshooting
- Requirements for your Build Logic
- Status
- Isolated Projects

# Securing Gradle Builds
Supply Chain Security

Verifying Dependencies

# IDE & Tool Integration
Third-party Tools

APIs
- Tooling API
- Test Reporting API

# How-To-Guides
Structuring Builds
- Convert a Single-Project Build to Multi-Project

Dependency Management
- How to Downgrade Transitive Dependencies
- How to Upgrade Transitive Dependencies
- How to Exclude Transitive Dependencies
- How to Prevent Accidental or Eager Dependency Upgrades
- How to Align Dependency Versions
- How to Share Outputs Between Projects
- How to Resolve Specific Artifacts from a Module Dependency
- How to Use a Local Fork of a Module Dependency
- How to Fix Version Catalog Problems
- How to Create Feature Variants of a Library

# See Also
