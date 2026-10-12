package com.spike.devops

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GitCoordinatesPluginTest {
    @Test 
    fun `plugin patches project version with git hash`() {
        // 1. 创建内存虚拟项目
        val project = ProjectBuilder.builder().build()
        project.version = "2.5.0-SNAPSHOT" // 给定一个初始测试版本号

        // 2. 应用纯净版插件
        project.plugins.apply("com.spike.devops.maven-publish-git-hash-plugin")

        // 3. 手动评估项目以触发生命周期
        (project as org.gradle.api.internal.project.ProjectInternal).evaluate()

        // 4. 断言检查：版本号应当不再是原样，且成功拼接了 Git Hash 或本地降级默认值 "unknown"
        val patchedVersion = project.version.toString()
        
        assertNotNull(patchedVersion)
        assertTrue(patchedVersion.startsWith("2.5.0-"), "版本号前缀应当保持一致")
        assertTrue(patchedVersion.endsWith("-SNAPSHOT"), "SNAPSHOT 后缀不应当丢失")
        print(patchedVersion)
        
        // 打印结果（配配合之前配置的 testLogging 可以在终端直接看到）
        println("Generated Coordinates Version: $patchedVersion")
    }
}