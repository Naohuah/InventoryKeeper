package me.starchier.inventorykeeper.configurations;

import me.starchier.inventorykeeper.InventoryKeeper;
import me.starchier.inventorykeeper.i18n.MessagesUtil;
import me.starchier.inventorykeeper.util.PluginHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ItemsConfig {
    public static void initItemsConfig(InventoryKeeper plugin, PluginHandler p) {
        File file = new File(plugin.getDataFolder(), "items.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.options().header(MessagesUtil.getMessage("commit.items-header"));
        //Config for default item
        config.addDefault("items.default.item-id", "STICK");
        //I don't know if it will works.
        if (config.getList("items").isEmpty()) {
            config.addDefault("items.default.item-name", MessagesUtil.getConfigValue("default-item-name"));
            config.addDefault("items.default.item-lore", MessagesUtil.getConfigArrayValue("default-item-lore"));
            config.addDefault("items.default.item-enchantments", Collections.singletonList(
                    (p.isLegacy() ? Enchantment.DURABILITY.getName() + "-10" : Enchantment.DURABILITY.getKey() + "-10")
            ));
            config.addDefault("items.default.save-exp", false);
            config.addDefault("items.default.disabled-worlds", new ArrayList<>());
            config.addDefault("items.default.exp-lose-percentage", "10-30");
            config.addDefault("items.default.run-commands-on-death", new ArrayList<>());
            config.addDefault("items.default.run-random-commands-on-death", new ArrayList<>());
            config.addDefault("items.default.run-commands-on-respawn", Collections.singletonList(
                    "15|90|effect %player% minecraft:fire_resistance %random% 1"
            ));
            config.addDefault("items.default.run-random-commands-on-respawn", Arrays.asList(
                    "10|30|effect %player% minecraft:strength %random% 1",
                    "10|30|effect %player% minecraft:speed %random% 1"
            ));
            config.addDefault("items.default.filter-entities-list.is-blacklist", true);
            config.addDefault("items.default.filter-entities-list.entities", new ArrayList<>());
            config.addDefault("items.default.filter-entities-name.is-blacklist", true);
            config.addDefault("items.default.filter-entities-name.names-list", new ArrayList<>());
            config.addDefault("items.default.items-with-lore-to-be-removed-on-death", Collections.singletonList(
                    "&6Soul bind"
            ));
            config.addDefault("items.default.death-message", MessagesUtil.getConfigValue("saved-inventory"));
            config.addDefault("items.default.priority", 10);
        }
        config.options().copyDefaults(true);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
