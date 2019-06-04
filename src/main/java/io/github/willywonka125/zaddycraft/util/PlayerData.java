package io.github.willywonka125.zaddycraft.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import io.github.willywonka125.zaddycraft.ZaddyCraft;

public class PlayerData extends AbstractFile {

	public PlayerData(ZaddyCraft zaddy, String filename) {
		super(zaddy, filename);
		// TODO Auto-generated constructor stub
	}
	
	public ConfigurationSection initPlayer(Player player) {
		return config.createSection(player.getUniqueId().toString());
	}
	
	public ConfigurationSection getPlayerData(Player player) {
		String uuid = player.getUniqueId().toString();
		//Initialize the player's section if it doesn't already exist
		return (config.isConfigurationSection(uuid)) ? config.getConfigurationSection(uuid) : initPlayer(player);
	}
	
	public ConfigurationSection getPlayerData(String uuid) {
		return (config.isConfigurationSection(uuid)) ? config.getConfigurationSection(uuid) : initPlayer(Bukkit.getPlayer(uuid));
	}
	
	public void setValue(Player player, String key, Object val) {
		getPlayerData(player).set(key, val);
	}

}
