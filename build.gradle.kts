plugins {
    pmd
    `java-library`
    id("name.remal.check-dependency-updates") version "1.0.126"
}
val eva = "1.1.0"
val p3c = "1.3.6"
val lombok = "1.18.6"

// libs
val lib = mapOf(
	"eva-core"		to "io.nerv:eva-core:${eva}",
	"eva-sys"		to "io.nerv:eva-sys:${eva}",
    "p3c"       	to "com.alibaba.p3c:p3c-pmd:${p3c}",
    "lombok"    	to "org.projectlombok:lombok:${lombok}",
    "spring-boot-devtools"	to "org.springframework.boot:spring-boot-devtools",
    "spring-processor"		to "org.springframework.boot:spring-boot-configuration-processor",
    "spring-test"			to "org.springframework.boot:spring-boot-starter-test"
)
// ext
extra["lib"] = lib

allprojects {
    group = "com.template"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "name.remal.check-dependency-updates")
    apply(plugin = "java-library")
    apply(plugin = "pmd")

     repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }

    dependencies {
       	pmd("${lib["p3c"]}")
        implementation("${lib["lombok"]}")
        annotationProcessor("${lib["lombok"]}")
     }

    // Java 版本
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
  
    // 代码检查配置
    configure<PmdExtension> {
        reportsDir = file("build/reports/pmd")

        ruleSets = listOf("java-ali-comment")
    }

      // 编译器配置
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.isDeprecation = true
        options.compilerArgs.add("-parameters")
    }
}

