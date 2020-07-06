package com.vhbob.soulbound;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class StopSoulboundMovement implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (!e.getPlayer().hasPermission("soulbound.ignore")) {
			if (isSoulbound(e.getItemDrop().getItemStack())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						ChatColor.RED + "You cannot drop soulbound items! If you are missing the item, please relog");
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player) || (e.getCursor() == null && e.getCurrentItem() == null)) {
			return;
		}
		ArrayList<InventoryType> validTypes = new ArrayList<InventoryType>();
		validTypes.add(InventoryType.ANVIL);
		validTypes.add(InventoryType.ENCHANTING);
		validTypes.add(InventoryType.ENDER_CHEST);
		validTypes.add(InventoryType.WORKBENCH);
		validTypes.add(InventoryType.PLAYER);
		validTypes.add(InventoryType.CRAFTING);
		validTypes.add(InventoryType.MERCHANT);
		Player player = (Player) e.getWhoClicked();
		player.sendMessage("You just clicked in an inventory of type " + e.getInventory().getType());
		if (!e.getWhoClicked().hasPermission("soulbound.ignore")
				&& (isSoulbound(e.getCursor()) || isSoulbound(e.getCurrentItem()))) {
			if (e.getInventory() == null || !validTypes.contains(e.getInventory().getType())) {
				player.sendMessage(ChatColor.RED
						+ "You cannot put soulbound items in there! If you are missing the item, please relog");
				e.setCancelled(true);
			}
		} else if (e.getClick() == ClickType.NUMBER_KEY && !validTypes.contains(e.getInventory().getType())) {
			if (player.getInventory().getItem(e.getHotbarButton()) != null
					&& isSoulbound(player.getInventory().getItem(e.getHotbarButton()))) {
				player.sendMessage(ChatColor.RED
						+ "You cannot put soulbound items in there! If you are missing the item, please relog");
				e.setCancelled(true);
			}
		}
	}

	private static boolean isSoulbound(ItemStack item) {
		if (item != null && item.hasItemMeta()) {
			if (item.getItemMeta().hasLore()) {
				if (item.getItemMeta().getLore()
						.contains(ChatColor.translateAlternateColorCodes('&', "&8[&5Soulbound&8]")))
					return true;
			}
		}
		return false;
	}

}
