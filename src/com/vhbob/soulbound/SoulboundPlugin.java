package com.vhbob.soulbound;

import org.bukkit.plugin.java.JavaPlugin;

public class SoulboundPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("Soulbind").setExecutor(new Soulbind());
		getServer().getPluginManager().registerEvents(new StopSoulboundMovement(), this);
	}

}
