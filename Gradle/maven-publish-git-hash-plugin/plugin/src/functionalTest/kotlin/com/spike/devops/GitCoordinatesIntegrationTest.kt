package com.spike.devops

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class GitCoordinatesIntegrationTest {

    // JUnit 5 会在每次运行测试前创建一个完全隔离的临时目录，模拟用户的代码仓
    @TempDir
    lateinit var testProjectDir: File

    @Test
    fun `publish task dynamic evaluation resolves coordinates with git hash`() {
        // 1. 创建临时的 settings.gradle.kts
        File(testProjectDir, "settings.gradle.kts").writeText("""
            rootProject.name = "integration-sandbox"
        """.trimIndent())

        // 2. 创建临时的 build.gradle.kts 脚本，在沙盒中应用我们的插件和 Maven 发布组件
        File(testProjectDir, "build.gradle.kts").writeText("""
            plugins {
                // 引入我们正在编写的插件
                id("com.spike.devops.maven-publish-git-hash-plugin")
                // 引入 Java 插件从而有 JAR 产物可以被发布
                java
            }
            
            group = "com.spike.test"
            version = "3.2.0-SNAPSHOT" // 模拟基础版本号
            
            // 声明一个简单的公共组件发布项
            publishing {
                publications {
                    create<MavenPublication>("mavenJava") {
                        from(components["java"])
                    }
                }
            }
        """.trimIndent())

        // 3. 运行真实的 Gradle 进程来模拟配置并执行发布到本地 Maven 仓库的任务
        val result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            // 运行 publishToMavenLocal，加上 --configuration-cache 验证是否完美兼容新版 Gradle 缓存特性
            .withArguments("publishToMavenLocal", "--configuration-cache") 
            .withPluginClasspath() // 关键：自动把当前正在编译的插件注入该隔离沙盒的类路径
            .forwardOutput()       // 关键：将沙盒后台进程的所有 System.out 日志实时倒流回当前的控制台
            .build()

        // 4. 断言检查：发布任务必须执行成功
        val publishTaskResult = result.task(":publishToMavenLocal")
        assertNotNull(publishTaskResult, "publishToMavenLocal 任务应当存在于 DAG 中")
        assertEquals(TaskOutcome.SUCCESS, publishTaskResult!!.outcome, "任务应当成功跑完而没有抛错")

        // 5. 验证物理产物：检查本地 build 目录下由 maven-publish 生成的 POM 文件
        // 确认我们插件拦截修改的版本号是否真的被固化写入了 XML 结构中
        val pomDir = File(testProjectDir, "build/publications/mavenJava")
        assertTrue(pomDir.exists() && pomDir.isDirectory, "Maven 发布插件应当成功输出了发布元数据")
        
        val pomFile = File(pomDir, "pom-default.xml")
        assertTrue(pomFile.exists(), "应当在物理磁盘上成功生成 pom-default.xml 描述文件")
        
        val pomContent = pomFile.readText()
        
        // 打印出最终拦截修改生成的 POM 文本（配合 forwardOutput 和 testLogging，你可以在控制台直接看）
        println("=== Verified Sandbox Extracted POM XML ===\n$pomContent\n==========================================")

        // 6. 核心逻辑断言：POM 里的版本号必须成功拼接了 Git Hash 或默认降级兜底字符
        assertTrue(pomContent.contains("<version>3.2.0-"), "POM 中的版本号前缀应当被成功拦截篡改")
        assertTrue(pomContent.contains("-SNAPSHOT</version>"), "SNAPSHOT 标识符在拼接完 Hash 后不应当丢失")
    }
}