package me.starchier.inventorykeeper.util;

import me.starchier.inventorykeeper.InventoryKeeper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler {
    private final InventoryKeeper plugin;
    private final PluginHandler pluginHandler;
    private final ItemHandler itemHandler;

    public CommandHandler(InventoryKeeper plugin, PluginHandler pluginHandler, ItemHandler itemHandler) {
        this.plugin = plugin;
        this.pluginHandler = pluginHandler;
        this.itemHandler = itemHandler;
    }

    public Player findPlayer(String pl) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(pl)) {
                return p;
            }
        }
        return null;
    }
    public void giveItem(CommandSender sender, String[] args) {
        Player target = findPlayer(args[3]);
        if (target == null) {
            sender.sendMessage(pluginHandler.getMessage("player-not-found").replace("%s", args[2]));
            return;
        }
        if (!itemHandler.isItem(args[2])) {
            sender.sendMessage(String.format(pluginHandler.getMessage("item-not-exist"), args[2]));
            return;
        }
        ItemStack item = itemHandler.buildItem(args[2]);
        if (args.length >= 4) {
            if (pluginHandler.isNumber(args[3])) {
                item.setAmount(Integer.parseInt(args[3]));
            } else {
                sender.sendMessage(pluginHandler.getMessage("is-not-number").replace("%s", args[3]));
                item.setAmount(1);
            }
        } else {
            item.setAmount(1);
        }
        target.getWorld().dropItem(target.getLocation(), item);
        sender.sendMessage(pluginHandler.getMessage("gave-item").replace("%s", String.valueOf(item.getAmount())).replace("%p", target.getDisplayName()));
        target.sendMessage(pluginHandler.getMessage("received-item").replace("%amount%", String.valueOf(item.getAmount())));
    }
}
