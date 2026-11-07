# 模板开发顶层规则

## 后端开发顶层规则（2026-11-07 合并）

基于本模板开发前，必须读取并遵守 [后端开发顶层规则](docs/DEVELOPMENT_RULES.md)，并与当前项目指令一并执行。使用项目自己的根包、分支及业务模块，不把本体或会务项目的配置照搬进新项目。复制模板时保留本入口和 docs/DEVELOPMENT_RULES.md。

## Gradle 版本目录（2026-11-05 用户确认）

Template 及基于模板新建项目的 Gradle 依赖坐标、依赖版本和插件版本必须统一在 gradle/libs.versions.toml 中声明；构建脚本必须通过版本目录别名（libs.*、libs.plugins.*）引用，不得在 build.gradle、build.gradle.kts 或 gradle.properties 中重复声明版本或硬编码依赖坐标。应用运行时配置不属于本条 TOML 约束；既有项目迁移须按任务授权范围执行。
