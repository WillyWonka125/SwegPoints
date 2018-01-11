package io.github.willywonka125.swegpoints;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Nerd implements CommandExecutor {
	
	public boolean isOnline(String name) {
		if (Bukkit.getPlayer(name) != null | false) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player snd = (Player) sender;
		if (args.length>0) { //Wait...
			if (cmd.getName().equalsIgnoreCase("nerd")) {
				if (sender.getName().equalsIgnoreCase("Makmak69") | sender.getName().equalsIgnoreCase("zaddyshack")) {
					sender.sendMessage(ChatColor.GOLD + "Attempting to smite " + ChatColor.GRAY + args[0]);
					if (isOnline(args[0])) {
						Player target = Bukkit.getPlayer(args[0]);
						World world = target.getWorld();
						Block targetBlock = world.getBlockAt(target.getLocation());
						world.strikeLightning(targetBlock.getLocation());
						PlayerInventory inv = target.getInventory();
						target.setHealth(1);
						Block pos = snd.getLocation().getBlock();
						pos.setType(Material.CHEST);
						pos.setType(Material.CHEST);
						Chest ch = (Chest) snd.getLocation().getBlock().getState();
						for (ItemStack i : inv.getContents()) {
							if (i != null) {
								ch.getInventory().addItem(i);
								sender.sendMessage("Added " + i.getType().name() + " to that chest right there");
							}
						}
						sender.sendMessage(ChatColor.GRAY + Bukkit.getPlayer(args[0]).getName() + ChatColor.GOLD + " hast been smitten.");
						sender.sendMessage(ChatColor.GRAY + " and also I put all their stuff in a chest in front of you");
						target.sendMessage(ChatColor.RED + "hey sorry man, but people just don't like you");
					} else {
						sender.sendMessage(ChatColor.RED + "My god, " + args[0] + " can't be found, dumbass.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Yeah you can't do that. Nobody can.");
				}
			}
			
		} else {
			sender.sendMessage(ChatColor.RED + "Try again...");
		}
		return true;
	}
	
	
}
