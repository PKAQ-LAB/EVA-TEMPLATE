plugins {
     id("org.springframework.boot") version "2.1.4.RELEASE"
 }
apply(plugin = "io.spring.dependency-management")

tasks.getByName<Jar>("jar") {
    enabled = true
}

springBoot {
    mainClassName = "com.xilai.WebBooter"
}

// 依赖配置
dependencies {
    val lib: Map<String, Any> by rootProject.extra

   
    implementation("${lib["eva-core"]}")
    implementation("${lib["eva-sys"]}")
    implementation("${lib["spring-boot-devtools"]}")

    annotationProcessor("${lib["spring-processor"]}")

    testCompile("${lib["spring-test"]}")
}
