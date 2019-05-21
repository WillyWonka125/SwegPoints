package io.github.willywonka125.swegpoints;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSpy implements Listener {
	
	List<Player> all = new ArrayList<Player>();;
	
	
	@EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isOp()) {
				p.sendMessage(ChatColor.AQUA + event.getPlayer().getName() + ": " + event.getMessage());
			}
		}
	}
	


}
