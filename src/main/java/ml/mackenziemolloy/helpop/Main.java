package ml.mackenziemolloy.helpop;

import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.nio.file.Paths;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.api.plugin.Plugin;

import static java.io.File.*;

public class Main extends Plugin {

    public static Main instance;
    public static Configuration cg;

    public void onEnable() {
        (Main.instance = this).createFiles();
        registerConfig();
        this.getProxy().getPluginManager().registerCommand(this, new HelpOp());
        this.getProxy().getPluginManager().registerCommand(this, new ReloadCommand());
        this.getProxy().getPluginManager().registerListener(this, new Notify());

        Main.instance.getProxy().getLogger().info(String.valueOf(Main.instance.getDataFolder().getAbsolutePath()));

    }

    private void createFiles() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        final File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                final InputStream in = this.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void registerConfig() {
        try {
            Main.cg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.instance.getDataFolder(), "config.yml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Main getInstance() {
        return Main.instance;
    }

    public static FileWriter writer;

    //public static File logFile = new File(Main.instance.getDataFolder().getAbsolutePath() + "\\logs.txt");
    static boolean file = new File("plugins/HelpOp/logs.txt").exists();

    static {

        try {
            if(file) {
                writer = new FileWriter("plugins/HelpOp/logs.txt", true);
            }
            else {
                writer = new FileWriter(new File("plugins/HelpOp/logs.txt"), true);
            }
            //new FileOutputStream() //Main.instance.getDataFolder().getAbsolutePath() +
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

