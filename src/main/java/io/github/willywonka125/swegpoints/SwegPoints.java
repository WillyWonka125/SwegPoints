package io.github.willywonka125.swegpoints;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


//Might have to make another class eventually.
public final class SwegPoints extends JavaPlugin {
	
	public static Plugin plugin;
	Leaderboard leaderboard = new Leaderboard();
	Nerd nerd = new Nerd(); //Heh...
	
	CommandExecutor nerdExecutor = (CommandExecutor) nerd;
	
	public void onEnable() {
		plugin = this;
		getLogger().info("SwegPoints is turning on!");
		getCommand("nerd").setExecutor(nerdExecutor);
		this.saveDefaultConfig();
		
	}
	
	public void onDisable() {
		this.saveConfig();
		plugin = null;
	}
	
	
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public void getPoints(Player player, Player sender) { //player is the target
		if (!(this.getConfig().isInt("playerdata." + player.getName() + ".points"))) {
			this.getConfig().set("playerdata." + player.getName() + ".points", 0);
			sender.sendMessage(ChatColor.GRAY + player.getName() + ChatColor.GOLD + " has 0 SwegPoints!");
		} else if (this.getConfig().isInt("playerdata." + player.getName() + ".points")) {
			sender.sendMessage(ChatColor.GRAY + player.getName() + ChatColor.GOLD + " has " + ChatColor.GRAY + this.getConfig().getInt("playerdata." + player.getName() + ".points") + ChatColor.GOLD + " SwegPoints!");
		}
	}
	
	public void givePoints(Player target, Player sender) {
		if (target == sender) {
			sender.sendMessage(ChatColor.RED + "You can't give yourself SwegPoints!");
		} else if (!(this.getConfig().isInt("playerdata." + target.getName() + ".points"))) { 
			this.getConfig().set("playerdata." + target.getName() + ".points", 1);
			sender.sendMessage(ChatColor.GOLD + "You gave " + ChatColor.GRAY + target.getName() + ChatColor.GOLD + " a SwegPoint!");
			target.sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GOLD + " gave you a SwegPoint!");
		} else if (this.getConfig().isInt("playerdata." + target.getName() + ".points")) {
			this.getConfig().set("playerdata." + target.getName() + ".points", this.getConfig().getInt("playerdata." + target.getName() + ".points") + 1);
			sender.sendMessage(ChatColor.GOLD + "You gave " + ChatColor.GRAY + target.getName() + ChatColor.GOLD + " a SwegPoint!");
			target.sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GOLD + " gave you a SwegPoint!");
		}
		this.saveConfig();
	}
	
	public boolean checkMateValidity(Player target) { //Need to return false if player is self...
		if (this.getConfig().isString("playerdata." + target.getName() + ".mate")) {
			if (this.getConfig().getString("playerdata." + target.getName() + ".mate").equalsIgnoreCase("none")) {
				return true;
			} else {
				return false;
			}
		} else if (!(this.getConfig().isString("playerdata." + target.getName() + ".mate"))) {
			this.getConfig().set("playerdata." + target.getName() + ".mate", "none");
			return true;
		}
		return true;
	}
	
	public void sendMateRequest(Player target, Player sender) {
		if (!(this.getConfig().isString("playerdata." + sender.getName() + ".mate"))) {
			this.getConfig().set("playerdata." + sender.getName() + ".mate", "none");
		}
		if (checkMateValidity(sender) == false) {
			sender.sendMessage(ChatColor.RED + "You already have a SwegMate!");
		} else if (checkMateValidity(target) == false) {
			sender.sendMessage(ChatColor.RED + target.getName() + " already has a SwegMate!");
		} else if (checkMateValidity(target) == true) {
			this.getConfig().set("playerdata." + sender.getName() + ".mate", "/" + target.getName());
			this.getConfig().set("playerdata." + target.getName() + ".mate", "/" + sender.getName());
			target.sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GOLD + " wishes to be your SwegMate! Do " + ChatColor.GRAY + "/sweg mate accept" + ChatColor.GOLD +  " to accept!");
			sender.sendMessage(ChatColor.GOLD + "Proposal sent!");
		}
		this.saveConfig();
	}
	
	public void acceptMateRequest(Player sender) {
		if (this.getConfig().getString("playerdata." + sender.getName() + ".mate").contains("/") && (!(this.getConfig().getString("playerdata." + sender.getName() + ".mate").equalsIgnoreCase(sender.getName())))) {
			Player target = Bukkit.getPlayer(this.getConfig().getString("playerdata." + sender.getName() + ".mate").substring(1));
			this.getConfig().set("playerdata." + sender.getName() + ".mate", target.getName());
			this.getConfig().set("playerdata." + target.getName() + ".mate", sender.getName());
			sender.sendMessage(ChatColor.GOLD + "You are now SwegMates with " + ChatColor.GRAY + target.getName());
			target.sendMessage(ChatColor.GRAY + sender.getName() + ChatColor.GOLD + " is now your SwegMate!");
		} else {
			sender.sendMessage(ChatColor.RED + "Nobody wants to be your SwegMate.");
		}
		this.saveConfig();
	} //Fucking mak, accept my mate request you whore.
	
	public void denyMateRequest(Player sender) { //reject that sorry ass
		if (this.getConfig().getString("playerdata." + sender.getName() + ".mate").contains("/")) {
			Player target = Bukkit.getPlayer(this.getConfig().getString("playerdata." + sender.getName() + ".mate").substring(1));
			this.getConfig().set("playerdata." + sender.getName() + ".mate", "none");
			this.getConfig().set("playerdata." + target.getName() + ".mate", "none");
			sender.sendMessage(ChatColor.GOLD + "You denied " + ChatColor.GRAY + target.getName() + ChatColor.GOLD + "'s SwegMate request!");
			target.sendMessage(ChatColor.GOLD + "You got rejected by " + ChatColor.GRAY + sender.getName() + ChatColor.GOLD + "!");
		} else {
			sender.sendMessage(ChatColor.RED + "There's nobody for you to deny!"); //Stone cold right there
		}
		this.saveConfig();
	}
	
	public void leaveMate(Player sender) {
		if (checkMateValidity(sender) == true) {
			sender.sendMessage(ChatColor.RED + "There's nobody to leave!");
		} else if (checkMateValidity(sender) == false) {
			this.getConfig().set("playerdata." + this.getConfig().getString("playerdata." + sender.getName() + ".mate"), "none");
			this.getConfig().set("playerdata." + sender.getName() + ".mate", "none");
			sender.sendMessage(ChatColor.RED + "You left your SwegMate!");
		}
	}
	
	public void getMate(Player sender, Player target) {
		if (checkMateValidity(target) == false) {
			sender.sendMessage(ChatColor.GRAY + target.getName() + ChatColor.GOLD + "'s mate is " + ChatColor.GRAY + this.getConfig().getString("playerdata." + target.getName() + ".mate"));
		} else if (checkMateValidity(target) == true) {
			sender.sendMessage(ChatColor.GRAY + target.getName() + ChatColor.GOLD + " doesn't have a SwegMate!");
		}
	}
	//fuck sch00l
	public void sendHelp(Player target) { 
		for (String line : help) {
			target.sendMessage(line);
		}
	}
	
	String[] help ={
			ChatColor.GOLD + "SwegPoints v0.1.1 by WillyWonka125",
			ChatColor.GRAY + "/sweg view [player]" + ChatColor.GOLD + " - View [player]'s SwegPoints",
			ChatColor.GRAY + "/sweg give <player>" + ChatColor.GOLD + " - Gives SwegPoints to <player>",
			ChatColor.GRAY + "/sweg mate <player>" + ChatColor.GOLD + " - Sends a SwegMate request to <player>",
			ChatColor.GRAY + "/sweg mate accept" + ChatColor.GOLD + " - Accepts a pending SwegMates request.",
			ChatColor.GRAY + "/sweg mate deny" + ChatColor.GOLD + " - Denies a pending SwegMates request",
			ChatColor.GRAY + "/sweg mate leave" + ChatColor.GOLD + " - Leaves your SwegMate",
			ChatColor.GRAY + "/sweg mate view [player]" + ChatColor.GOLD + " - View [player]'s SwegMate",
	};
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sweg")) {
			if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")) {
				sendHelp((Player) sender);
				return true;
			} else if (args[0].equalsIgnoreCase("view")) {
				getLogger().info("Sub-command is view");
				if (args.length == 1) {
					getLogger().info("And there is no player to view.");
					if (!(this.getConfig().isInt("playerdata." + sender.getName() + ".points"))) {
						this.getConfig().set("playerdata." + sender.getName() + ".points", 0);
						sender.sendMessage(ChatColor.GOLD + "You have 0 SwegPoints!");
						return true;
					} else if (this.getConfig().isInt("playerdata." + sender.getName() + ".points")) {
						sender.sendMessage(ChatColor.GOLD + "You have " + ChatColor.GRAY + this.getConfig().get("playerdata." + sender.getName() + ".points") + ChatColor.GOLD + " SwegPoints!");
						return true;
					}
					
				} else if (!(Bukkit.getPlayer(args[1]) == null)) {
					getLogger().info("Getting point count for " + Bukkit.getPlayer(args[1]).getName());
					getPoints(Bukkit.getPlayer(args[1]), Bukkit.getPlayer(sender.getName()));
					getLogger().info("Fired getPoints.");
					return true;
				} else if (Bukkit.getPlayer(args[1]) == null) {
					getLogger().info("Player to view can't be found.");
					sender.sendMessage(args[1] + " can't be found!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("leaderboard")) { //Finally moved something to another class
				sender.sendMessage("Yeah, this is 0.1.1");
				return true;
			} else if (args[0].equalsIgnoreCase("give")) {
				getLogger().info("Sub command is give");
				if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Not enough arguments! Do /sweg help for help.");
					return true;
				} else if (!(Bukkit.getPlayer(args[1]) == null)) {
					getLogger().info("Giving a point to " + Bukkit.getPlayer(args[1]).getName());
					givePoints(Bukkit.getPlayer(args[1]), Bukkit.getPlayer(sender.getName()));
					return true;
				} else if (Bukkit.getPlayer(args[1]) == null) {
					getLogger().info("Player couln't be found.");
					sender.sendMessage(ChatColor.RED + args[1] + " can't be found!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("mate")) {
				if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Not enough arguments! Do /sweg help for help.");
					return true;
				} else if (!(Bukkit.getPlayer(args[1]) == null)) {
					sendMateRequest(Bukkit.getPlayer(args[1]), (Player) sender);
					return true;
				} else if (args[1].equalsIgnoreCase("accept")) {
					acceptMateRequest((Player) sender);
					return true;
				} else if (args[1].equalsIgnoreCase("deny")) {
					denyMateRequest((Player) sender);
					return true;
				} else if (args[1].equalsIgnoreCase("leave")) {
					leaveMate((Player) sender);
					return true;
				}else if (args[1].equalsIgnoreCase("view")) {
					if (args.length == 2) {
						sender.sendMessage(ChatColor.GOLD + "Your SwegMate is " + ChatColor.GRAY + this.getConfig().getString("playerdata." + sender.getName() + ".mate"));
						return true;
					} else if (!(Bukkit.getPlayer(args[2]) == null)) {
						getMate((Player) sender, Bukkit.getPlayer(args[2]));
					} else if (Bukkit.getPlayer(args[2]) == null) {
						sender.sendMessage(ChatColor.RED + args[0] + " can't be found!");
						return true;
					}
				} else if (args.length > 1) {
					sender.sendMessage(ChatColor.RED + "Unknown sub-command! Do " + ChatColor.GRAY + "/sweg help" + ChatColor.RED + " for help!");
					return true;
				}
			}
		}
		getLogger().info("Command ran by " + sender.getName() + ", SwegPoints v0.1.1");
		return true;
	}
}
