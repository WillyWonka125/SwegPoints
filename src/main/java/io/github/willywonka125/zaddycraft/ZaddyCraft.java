package io.github.willywonka125.zaddycraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.willywonka125.zaddycraft.util.PlayerData;

public class ZaddyCraft extends JavaPlugin{
	
	//Version
	String ver = "Dev-0.1";
	
	public static ZaddyCraft plugin;
	
	//Constructors (these don't need an instance of ZaddyCraft to be passed off)
	private Nerd nerd = new Nerd(); //Heh...
	private drawDick drawDick = new drawDick();
	
	private SwegPoints SwegPoints;
	private CommandSpy cspy;
	public PlayerData playerData;
	
	public void onEnable() {
		getLogger().info("ZaddyCraft version " + ver);
		
		plugin = this;
		
		if (!getDataFolder().exists()) { getDataFolder().mkdirs(); } //Create the plugin's data folder before calling file constructors
		
		this.playerData = new PlayerData(this, "playerdata.yml");
		this.cspy = new CommandSpy(this);
		this.SwegPoints = new SwegPoints(playerData);
		
		registerCommands();
		Bukkit.getPluginManager().registerEvents(cspy, this);
	}
	
	public void onDisable() {
		playerData.save();
	}
	
	public void registerCommands() {
		getCommand("sweg").setExecutor(SwegPoints);
		getCommand("nerd").setExecutor(nerd);
		getCommand("dick").setExecutor(drawDick);
		getCommand("cspy").setExecutor(cspy);
		
	}
	
	public String[] help = {
			ChatColor.RED + "ZaddyCraft " + ver+ " administrative commands",
			ChatColor.GRAY + "/zc reload: " + ChatColor.RED + "Reloads playerdata.yml"
	};
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("zc")) {
			if (args.length < 1) {
				sender.sendMessage(help);
			} else {
				switch (args[0]) {
				case "help":
					sender.sendMessage(help);
				case "reload":
					playerData.reload();
				}
			}
		}
		return true;
	}
	

}
