package io.github.willywonka125.swegpoints;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class Nerd implements CommandExecutor {
	
	public boolean isOnline(String name) {
		if (Bukkit.getPlayer(name) != null | false) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length>0) { //Wait...
			if (cmd.getName().equalsIgnoreCase("nerd")) {
				if (sender.getName().equalsIgnoreCase("Makmak67") | sender.getName().equalsIgnoreCase("NuclearTitties")) {
					sender.sendMessage(ChatColor.GOLD + "Attempting to smite " + ChatColor.GRAY + args[0]);
					if (isOnline(args[0])) {
						Player target = Bukkit.getPlayer(args[0]);
						World world = target.getWorld();
						Block targetBlock = world.getBlockAt(target.getLocation());
						world.strikeLightning(targetBlock.getLocation());
						target.setHealth(0);
						sender.sendMessage(ChatColor.GRAY + Bukkit.getPlayer(args[0]).getName() + ChatColor.GOLD + " hast been smitten.");
						target.sendMessage(ChatColor.RED + "Mak killed you because she sucks. Sorry.");
					} else {
						sender.sendMessage(ChatColor.RED + "My god, " + args[0] + " can't be found, dumbass.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You aren't Mak. Go away.");
				}
			}
			
		} else {
			sender.sendMessage(ChatColor.RED + "Need more arguments, stupid!");
		}
		return true;
	}
}
