plugins {
    pmd
    `java-library`
    id("name.remal.check-dependency-updates") version "1.0.160"
}
val eva = "2.0.6.beta"
val lombok = "1.18.10"

// libs
val lib = mapOf(
	"eva-core"		to "io.nerv:eva-core:${eva}",
	"eva-sys"		to "io.nerv:eva-sys:${eva}",
    "lombok"    	to "org.projectlombok:lombok:${lombok}",
    "spring-boot-devtools"	to "org.springframework.boot:spring-boot-devtools",
    "spring-processor"		to "org.springframework.boot:spring-boot-configuration-processor",
    "spring-test"			to "org.springframework.boot:spring-boot-starter-test"
)
// ext
extra["lib"] = lib

allprojects {
    group = "tech.xilai"
    version = "1.0.0"
}

subprojects {
    apply(plugin = "name.remal.check-dependency-updates")
    apply(plugin = "java-library")

     repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
		maven("https://dl.bintray.com/nerv/pub")
        maven("http://192.168.10.166:10081/artifactory/local-repo")
		maven("http://maven.springframework.org")
        maven("https://repo.spring.io/libs-release")
		maven("https://mirrors.huaweicloud.com")
    }

    dependencies {
       	implementation("${lib["lombok"]}")
        annotationProcessor("${lib["lombok"]}")
     }

    // Java 版本
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

      // 编译器配置
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.isDeprecation = true
        options.compilerArgs.add("-parameters")
    }
}

