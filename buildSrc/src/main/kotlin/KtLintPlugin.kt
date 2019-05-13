import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.*

class KtLintPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            val ktlint: Configuration by project.configurations.creating

            dependencies {
                ktlint("com.pinterest", "ktlint", "0.32.0")
            }

            tasks {
                register<JavaExec>("ktlint") {
                    group = "verification"
                    description = "Check Kotlin code style"
                    main = "com.pinterest.ktlint.Main"
                    classpath = ktlint
                    args = listOf("src/**/*.kt")
                }

                named("check") {
                    dependsOn("ktlint")
                }

                register<JavaExec>("ktlintFormat") {
                    description = "Fix Kotlin code style deviations."
                    main = "com.pinterest.ktlint.Main"
                    classpath = ktlint
                    args = listOf("-F", "--experimental", "src/**/*.kt")
                }

                named("classes") {
                    dependsOn("ktlintFormat")
                }

                named("testClasses") {
                    dependsOn("ktlintFormat")
                }
            }
        }
    }
}
