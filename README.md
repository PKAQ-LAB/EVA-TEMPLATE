# Template-API

基于 Nexus 正式版 EVA `1.1.2` 的应用启动模板，保留应用包名 `io.nerv`。

## 基线

- Java 25、Spring Boot 4.1.2、Gradle 9.5.1（使用本机安装，无 Wrapper）。
- EVA：`eva-core-common`、`eva-core-data-mybatis`、`eva-web-core`、`eva-web-auth`、`eva-web-sys-service`。其余内部依赖由发布的 Maven 元数据传递解析。
- PostgreSQL、Redis、Springdoc 3.2.0；不再依赖旧 `eva-core-web`／聚合 `eva-web-sys`、MySQL 驱动或旧缓存／日志 supporter 配置。
- 依赖仓库：`http://dev.jm-sk.com:12081/repository/maven-public/`。优先解析已发布制品，不用 `mavenLocal()` 掩盖私服缺包；项目无发布任务。

## 构建与环境切换

```powershell
gradle :api:test :api:bootJar --no-parallel --max-workers=1
$env:SPRING_PROFILES_ACTIVE = 'dev'
$env:EVA_DB_URL = 'jdbc:postgresql://localhost:5432/template_api?currentSchema=eva'
$env:EVA_DB_USERNAME = 'postgres'
$env:EVA_DB_PASSWORD = '<本机数据库密码>'
$env:EVA_REDIS_HOST = 'localhost'
$env:EVA_REDIS_PASSWORD = '<本机Redis密码>'
java -jar api/build/libs/api-1.0.0.jar
```

默认环境 `dev`，端口 `9016`，context-path `/api`；不要与本机其他占用9016的服务同时启动。环境在运行时切换，不再构建替换 `@profile@`。旧 `aliyun` 环境名作为生产配置兼容入口，不保留旧远程服务连接信息。

生产使用 `SPRING_PROFILES_ACTIVE=prod`，必须配置 `EVA_DB_URL`、`EVA_DB_USERNAME`、`EVA_DB_PASSWORD`、`EVA_REDIS_HOST`、`EVA_REDIS_PASSWORD` 和至少32字节的 `EVA_JWT_SECRET`。生产关闭接口文档，Cookie 采用 secure。不要将生产凭据提交到仓库。

可选 Nexus 凭据在 Gradle 用户目录的 `gradle.properties` 中配置 `nexusUsername`／`nexusPassword`，不要复制 EVA-API 本机 `publish.gradle` 或凭据。

## EVA 配置

`application*.yaml` 只维护 Spring／数据源／Redis／端口等基础设施；`config/eva/eva*.yaml` 承载 EVA 认证、权限、租户、上传、日志归档等业务配置，并按活动 profile 加载。

默认单体且启用认证、接口资源权限和数据权限；注册及许可证验证默认关闭。需要 schema 租户时明确设置 `EVA_MODE=platform`、`EVA_TENANT_ENABLE=true`、`EVA_TENANT_MODE=schema`、`EVA_CORE_SCHEMA`。这只是配置入口，不会因测试或构建自动创建 schema。应用和EVA组件一同扫描，字典缓存初始化交由EVA生命周期管理，不在启动类手工调用旧缓存API。

## 数据库初始化

当前 `db/migration` 仅保留最新 PostgreSQL `V1__INITDB.sql`，另附 `db/tenant-migration/V1__TENANT_SCHEMA_BASE.sql`。Flyway 默认关闭且不自动 baseline；只能在准备好的新空库、确认 schema 与初始化账号后显式启用。既有MySQL或非空库不能重跑此V1，迁移需单独规划。

旧MySQL基础库与进销存SQL原样存放在 `docs/legacy-migrations`，不在应用classpath中，不会参与Flyway。旧license文件也仅保留，默认不启用验证。未对任何真实数据库执行结构或数据操作。

## 验证边界

`EvaConfigurationTest` 使用发布的真实EVA配置绑定器与Spring ConfigData验证开发／生产／schema覆盖和生产密钥校验，不启动应用Web服务，不连接数据库或Redis。编译、打包与配置测试不能替代真实环境登录、CRUD和权限联调；运行联调需另行提供目标环境配置。

## 开发规则

业务开发前阅读 [后端开发顶层规则](docs/DEVELOPMENT_RULES.md)，规则入口见 [AGENTS.md](AGENTS.md)。复制本模板时一并保留这两个文件。
