package com.spike.devops

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class GitCoordinatesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 自动拉起必要的基础 Maven 发布插件
        project.plugins.apply("maven-publish")

        // 1. 惰性获取并计算带 Git Hash 的版本号
        val gitHashProvider = project.providers.of(GitHashValueSource::class.java) {}
        val baseVersion = project.provider { project.version.toString() }
        
        val customVersionProvider = baseVersion.zip(gitHashProvider) { version, hash ->
            if (version == Project.DEFAULT_VERSION || version.isBlank() || version == "unspecified") {
                "1.0.0-$hash"
            } else if (version.endsWith("-SNAPSHOT")) {
                "${version.removeSuffix("-SNAPSHOT")}-$hash-SNAPSHOT"
            } else {
                "$version-$hash"
            }
        }

        // 强行修正项目的核心元数据
        project.version = customVersionProvider.get()

        // 2. 动态拦截 Maven 发布配置（使用强类型显式访问）
        project.extensions.configure(PublishingExtension::class.java) {ext ->
            // 通过 this.publications 显式拿到出版物容器
            ext.publications.withType(MavenPublication::class.java).configureEach { 
                // 显式为每个 MavenPublication 实例复写它的 version 属性
                it.version = customVersionProvider.get()
            }
        }
    }
}