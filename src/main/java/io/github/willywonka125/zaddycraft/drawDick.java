package io.github.willywonka125.swegpoints;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class drawDick implements CommandExecutor {
	
	
	public String build(Location targ, ItemStack i, int size, Player sender){
		if (size < 1) {
			return ChatColor.RED + "i can't draw a dick that short";
		}
		
		if (i.getAmount() < size + 2) {
			return ChatColor.RED + "you need more blocks to build a dick of that size. consider supplements";
		}
		
		int x = targ.getBlockX();
		int y = targ.getBlockY() + 1; //Y will then return the block above the line of sight
		int z = targ.getBlockZ();
		World world = targ.getWorld(); //Let's get the world to make setting the blocks easier
		
		//We must figure out which way player is facing so that the dick will face them

        double rot = (sender.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
		
		
		//First, draw the balls
		if (getDirection(rot).equalsIgnoreCase("east") || getDirection(rot).equalsIgnoreCase("west")) { //Facing positive Z, so offset by X
			world.getBlockAt(x - 1, y, z).setType(i.getType());
			world.getBlockAt(x + 1, y, z).setType(i.getType());
		} else if (getDirection(rot).equalsIgnoreCase("north") || getDirection(rot).equalsIgnoreCase("south")) {
			world.getBlockAt(x, y, z - 1).setType(i.getType());
			world.getBlockAt(x, y, z + 1).setType(i.getType());
		}
		
		//Now onto the shaft
		for (int j=1; j <=size; j++) {
			world.getBlockAt(x,y+j,z).setType(i.getType());
		}
		
		//And that should be it!
		
		return ChatColor.DARK_PURPLE + "Drew a dick out of " + i.getType().name();
	};
	
    private static String getDirection(double rot) {
        if (0 <= rot && rot < 45) {
            return "north";
        } else if (46 <= rot && rot < 135) {
            return "east";
        } else if (136 <= rot && rot < 225) {
            return "south";
        } else if (226 <= rot && rot < 315) {
            return "west";
        } else if (316 <= rot && rot < 360) {
        	return "north";
        } else {
            return null;
        }
    }
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage("yeah it works");
		if (cmd.getName().equalsIgnoreCase("dick")) {
			int s = 0;
			Player plr = (Player) sender;
			if (args.length > 0) {

				try {
					s = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "that isn't a number.");
					return true;
				}
		
				if (s < 2) {
					s = 3;
				}

			}
			else {
				s = 3;

			}
			if (plr.getGameMode().equals(GameMode.SURVIVAL)) {
				plr.sendMessage(build(plr.getTargetBlock((Set<Material>)null, 200).getLocation(), plr.getInventory().getItemInMainHand(), s, plr));
				if (plr.getInventory().getItemInMainHand().getAmount() >= s+2) {
					plr.getInventory().getItemInMainHand().setAmount(plr.getInventory().getItemInMainHand().getAmount() - (s+2));
				}
			} else {
				plr.sendMessage(build(plr.getTargetBlock((Set<Material>)null, 200).getLocation(), plr.getInventory().getItemInMainHand(), s, plr));
			}
		}
		return true;
	}
}
