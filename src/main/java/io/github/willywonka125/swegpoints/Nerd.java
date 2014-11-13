package io.github.willywonka125.swegpoints;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nerd {
	
	public boolean isOnline(String name) {
		if (Bukkit.getPlayer(name) != null | false) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("nerd")) {
			if (sender.getName().equalsIgnoreCase("Makmak67") | sender.getName().equalsIgnoreCase("NuclearTitties")) {
				sender.sendMessage("Attempting to smite " + args[0]);
				if (isOnline(args[0])) {
					Player target = Bukkit.getPlayer(args[0]);
					World world = target.getWorld();
					Block targetBlock = world.getBlockAt(target.getLocation());
					world.strikeLightning(targetBlock.getLocation());
					sender.sendMessage(Bukkit.getPlayer(args[0]) + " hast been smitten.");
					target.sendMessage("Mak killed you because she sucks. Sorry.");
				} else {
					sender.sendMessage("My god, " + args[0] + " can't be found, dumbass.");
				}
			} else {
				sender.sendMessage("You aren't Mak. Go away.");
			}
		}
		
		return true;
	}
}
