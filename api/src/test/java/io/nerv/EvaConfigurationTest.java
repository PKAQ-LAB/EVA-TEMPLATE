package io.nerv;

import org.junit.jupiter.api.Test;
import org.pkaq.core.properties.EvaConfig;
import org.pkaq.core.properties.EvaPropertiesConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 验证模板配置可由已发布 EVA 绑定，不连接数据库或 Redis。
 *
 * @author PKAQ
 * @date 2026-11-02
 */
class EvaConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer())
            .withUserConfiguration(EvaPropertiesConfiguration.class);

    @Test
    void shouldLoadDevelopmentDefaultsFromSeparateEvaConfig() {
        runner.withPropertyValues("spring.profiles.active=dev").run(context -> {
            assertThat(context).hasNotFailed();
            EvaConfig config = context.getBean(EvaConfig.class);
            assertThat(config.getMode()).isEqualTo("standalone");
            assertThat(config.getAuth().isAuthenticationEnabled()).isTrue();
            assertThat(config.getResourcePermission().isEnable()).isTrue();
            assertThat(config.getTenant().isEnable()).isFalse();
            assertThat(context.getEnvironment().getProperty("spring.flyway.enabled")).isEqualTo("false");
            assertThat(context.getEnvironment().getProperty("spring.datasource.url"))
                    .contains("jdbc:postgresql:");
            assertThat(context).doesNotHaveBean("dataSource");
        });
    }

    @Test
    void shouldApplyProductionSecretAndCookiePolicyAtRuntime() {
        runner.withPropertyValues("spring.profiles.active=prod",
                "EVA_JWT_SECRET=isolated-production-test-key-at-least-32-bytes").run(context -> {
            assertThat(context).hasNotFailed();
            EvaConfig config = context.getBean(EvaConfig.class);
            assertThat(config.getJwt().getSecret()).startsWith("isolated-production-test");
            assertThat(context.getEnvironment().getProperty("eva.cookie.secure")).isEqualTo("true");
            assertThat(context.getEnvironment().getProperty("springdoc.api-docs.enabled")).isEqualTo("false");
            assertThat(context).doesNotHaveBean("dataSource");
        });
    }

    @Test
    void shouldRejectProductionWithEmptySecret() {
        runner.withPropertyValues("spring.profiles.active=prod", "EVA_JWT_SECRET=").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).hasMessageContaining("eva.jwt.secret");
        });
    }

    @Test
    void shouldBindSchemaModeWithoutProvisioningDatabase() {
        runner.withPropertyValues("spring.profiles.active=dev", "EVA_MODE=platform",
                "EVA_TENANT_ENABLE=true", "EVA_TENANT_MODE=schema",
                "EVA_CORE_SCHEMA=template_core").run(context -> {
            assertThat(context).hasNotFailed();
            EvaConfig config = context.getBean(EvaConfig.class);
            assertThat(config.getMode()).isEqualTo("platform");
            assertThat(config.getTenant().isEnable()).isTrue();
            assertThat(config.getTenant().getCoreSchema()).isEqualTo("template_core");
            assertThat(context).doesNotHaveBean("dataSource");
        });
    }
}
