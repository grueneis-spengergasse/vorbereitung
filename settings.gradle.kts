rootProject.name = "POS_3xhif_java_seed"

pluginManagement {
    repositories {
        if (System.getProperty("os.name").lowercase().contains("windows")) {
            maven("https://artifactory.htl-wien5.schule/repository/gradle-plugin/")
        } else {
            gradlePluginPortal()
        }
    }
}
