package io.github.willywonka125.zaddycraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import io.github.willywonka125.zaddycraft.util.PlayerData;



public class SwegPoints implements CommandExecutor, Listener {
	
	private PlayerData pd;
	
	public SwegPoints (PlayerData playerdata) {
		this.pd = playerdata;
	}

	@Override
	public boolean onCommand(CommandSender snd, Command cmd, String CommandLabel, String[] args) {
		Player plr = null;
		try {
			plr = (Player) snd;
		} catch (CommandException e) {
			Bukkit.getLogger().info("Error while running command (perhaps you are using the console?");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("sweg")) {
			if (args.length < 1) { snd.sendMessage("Help message"); } else {
				switch (args[0]) {
				case "view":
					snd.sendMessage(viewPoints(plr, args[1]));
				case "give":
					switch (args.length) {
					case 1:
						snd.sendMessage(ChatColor.RED + "Missing arguments");
						snd.sendMessage(ChatColor.RED + "/sweg give <player> [amount]");
					case 2:
						givePoints(plr, args[1], 1);
					case 3:
						try {
							givePoints(plr, args[1], Integer.parseInt(args[2]));
						} catch (NumberFormatException e) {
							snd.sendMessage(ChatColor.RED + "Invalid amount");
						}
					}

				}
			}
		}
		return true;
	}
	
	public String viewPoints(Player sender, String target) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(target);
		if (op.hasPlayedBefore()) {
			return ChatColor.translateAlternateColorCodes('&', "&e"+ target + " has &7 " + getPoints(op.getUniqueId().toString()) + " &eSwegPoints");
		} else { return ChatColor.RED + "Player not found"; }
	}
	
	public int getPoints(String uuid) {
		if (pd.getPlayerData(uuid).isConfigurationSection("swegpoints")) {
			return pd.getPlayerData(uuid).getInt("swegpoints.points");
		} else {
			pd.getPlayerData(uuid).createSection("swegpoints").set("points", 0);
			return 0;
		}
	}
	
	public void givePoints(Player sender, String target, int amount) { //This doesn't protect against sending 0 points, but do we really want that?
		Player targ = Bukkit.getPlayer(target);
		if (targ != null && !targ.equals(sender)) {
			String uuid = targ.getUniqueId().toString();
			int setTo = getPoints(uuid) + amount;
			pd.getPlayerData(uuid).set("swegpoints.points", setTo);
			targ.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + sender.getName() + " &7gave you &e" + amount + " &7SwegPoints!"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You gave &e" + target + amount + " &7SwegPoints!"));
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid player");
		}
	}
	
	public void givePoints(Player target, int amount) {
		//We know target is online because we were given a Player instance
		int setTo = getPoints(target.getUniqueId().toString()) + amount;
		pd.getPlayerData(target).getInt("swegpoints.points", setTo);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		givePoints(e.getEntity(), 1);
		e.getEntity().sendMessage(ChatColor.GOLD + "You've been awarded 1 SwegPoint for dying.");
	}

}
