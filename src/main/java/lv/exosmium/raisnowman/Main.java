package lv.exosmium.raisnowman;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    private File configFile;
    private FileConfiguration config;
    private static Main instance;

    public Main() {
        this.configFile = new File(this.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    @Override
    public void onEnable() {
        getLogger().info("enabled!");

        setupConfig();
        instance = this;
        this.getCommand("snowman").setExecutor(new SnowmanCommand());
        Bukkit.getPluginManager().registerEvents(new SnowmanListener(), this);
    }

    private void setupConfig() {
        if (!this.configFile.exists()) {
            this.config.options().copyDefaults(true);
            this.saveDefaultConfig();
        }
    }

    public static Main getInstance() {
        return instance;
    }
}
