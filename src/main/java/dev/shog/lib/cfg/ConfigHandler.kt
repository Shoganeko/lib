package dev.shog.lib.cfg

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import dev.shog.lib.FileHandler
import org.json.JSONObject
import java.io.File
import java.lang.Exception

/**
 * Manages configuration files.
 */
object ConfigHandler {
    /**
     * Get a config using a [configType] and [applicationName].
     *
     * @param configType The type the config is.
     * @param applicationName The name of the application.
     * @return A [Config] instance.
     */
    fun getConfig(configType: ConfigType, applicationName: String): Config {
        val file = File(FileHandler.getApplicationFolder(applicationName).path + File.separator + "cfg." + configType.extension)
        val data = String(file.inputStream().readBytes())

        return when (configType) {
            ConfigType.JSON ->
               Config(JSONObject(data))

            ConfigType.YML -> {
                val json = ObjectMapper(YAMLFactory()).readTree(data)

                Config(JSONObject(json.toString()))
            }
        }
    }

    /**
     * Create a config using [configType] and [applicationName].
     *
     * @param configType The type of config to create.
     * @param cfg The object to write.
     * @param applicationName The application to create the config for.
     * @param overwrite If the config exists, overwrite it? If this is false, this will still return a [Config] instance.
     * @return A [Config] instance.
     */
    fun <T> createConfig(configType: ConfigType, applicationName: String, cfg: T, overwrite: Boolean = false): Config {
        val file = File(FileHandler.getApplicationFolder(applicationName).path + File.separator + "cfg." + configType.extension)

        if (file.exists() && !overwrite)
            return getConfig(configType, applicationName)

        file.createNewFile()

        when (configType) {
            ConfigType.YML -> ObjectMapper(YAMLFactory()).writeValue(file, cfg)
            ConfigType.JSON -> ObjectMapper().writeValue(file, cfg)
        }

        return getConfig(configType, applicationName)
    }

    /**
     * The type of file the config is.
     */
    enum class ConfigType(val extension: String) {
        YML("yml"), JSON("json")
    }
}