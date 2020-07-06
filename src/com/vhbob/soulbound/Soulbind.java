package com.vhbob.soulbound;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Soulbind implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Soulbind")) {
			if (sender.hasPermission(cmd.getPermission())) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
						ItemStack item = player.getItemInHand();
						ArrayList<String> lore = new ArrayList<>();
						if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
							lore = (ArrayList<String>) item.getItemMeta().getLore();
						}
						if (!lore.contains(ChatColor.translateAlternateColorCodes('&', "&8[&5Soulbound&8]")))
							lore.add(ChatColor.translateAlternateColorCodes('&', "&8[&5Soulbound&8]"));
						ItemMeta meta = item.getItemMeta();
						meta.setLore(lore);
						item.setItemMeta(meta);
						player.sendMessage(ChatColor.GREEN + "Soulbound the item");
					} else {
						player.sendMessage(ChatColor.RED + "You must be holding an item");
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Missing permission: " + cmd.getPermission());
			}
		}
		return false;
	}

}
