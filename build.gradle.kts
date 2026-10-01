import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.navigation.safe.gradle) apply false
    alias(libs.plugins.hilt.android.gradle) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint)
}

subprojects {
    if (path.startsWith(":info")) {
        apply(plugin = "org.jlleitschuh.gradle.ktlint")

        configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            android.set(true)
            ignoreFailures.set(false)
            reporters {
                reporter(ReporterType.PLAIN)
                reporter(ReporterType.SARIF)
                reporter(ReporterType.CHECKSTYLE)
            }
        }
    }
}
