plugins {
    application
    id("java")
    id("com.diffplug.spotless") version "8.10.0"
    jacoco
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.22.3")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "hexlet.code.App"
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat().aosp()
        formatAnnotations()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
}

jacoco {
    toolVersion = "0.8.14"
}
val coverageExcludes = listOf("hexlet/code/App.class")

fun JacocoReportBase.excludeEntryPoint() {
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(coverageExcludes)
            }
        })
    )
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    excludeEntryPoint()

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    excludeEntryPoint()

    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}