package fr.keykatyu.mctranslation;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§a§l[MCTranslationLib] Library loaded successfully.");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§c§l[MCTranslationLib] Library unloaded.");
    }

}
