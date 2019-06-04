package io.github.willywonka125.zaddycraft;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import io.github.willywonka125.zaddycraft.util.PlayerData;

public class CommandSpy implements Listener, CommandExecutor {
	
	private ZaddyCraft zc;
	private PlayerData pd;
	
	public CommandSpy(ZaddyCraft zaddycraft) {
		this.zc = zaddycraft;
		this.pd = zaddycraft.playerData;
	}
	

	
	@EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isOp() && !p.equals(event.getPlayer())) { //Don't forget to change p.isOp() once that feature has been built
				p.sendMessage(ChatColor.AQUA + event.getPlayer().getName() + ": " + event.getMessage());
			}
		}
	}

	String[] help ={
			ChatColor.AQUA + "CommandSpy options",
			ChatColor.GRAY + "/cspy toggle [player]" + ChatColor.AQUA + " - Toggle CommandSpy on/off for yourself or [player]",
			ChatColor.GRAY + "/cspy exempt [player]" + ChatColor.AQUA + " - Your/[player]'s commands won't be shown by CommandSpy"
	};
	
	//Methods relating to toggling and exemption will be here
	public String toggle(Player plr) {
		if (pd.getPlayerData(plr).isBoolean("cspy") && pd.getPlayerData(plr).getBoolean("cspy")) {
			pd.getPlayerData(plr).set("cspy", false);
			return (ChatColor.AQUA + "CommandSpy turned off");
		} else {pd.getPlayerData(plr).set("cspy", true); return (ChatColor.AQUA + "CommandSpy turned on");}
	}
	

	//Will handle turning cspy on and off for players here
	public boolean onCommand(CommandSender snd, Command cmd, String commandLabel, String[] args) {
		Player plr = (Player) snd;
		
		if (cmd.getName().equalsIgnoreCase("cspy")) {
			if (args.length < 1) {snd.sendMessage(help);} else {
				switch (args[0]) {
				case "help":
					snd.sendMessage(help);
					break;
				case "toggle":
					plr.sendMessage(toggle(plr));
				}
			}
		}
		return true;
	}
	


}
