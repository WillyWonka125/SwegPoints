package io.github.willywonka125.swegpoints;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;


public class Leaderboard {
	
	int[] scores = new int[10];
	String[] names = new String[10];
	int max = 0;
	int min = 0;
	
	SwegPoints main = new SwegPoints();
	ConfigurationSection data = main.getConfig().getConfigurationSection("playerdata");
	
	public void addScore (int score, String name) { //Kinda stole this code from a few other plugins... sorry?
		
		int num = 0;
		boolean inLeaders = false;
		boolean end = false;
		
		for (int i=9; i>=0 && !end; i--) {
			if (score > scores[i]) {
				inLeaders = true;
				num = i; 
			} else {
				end = true;
			}
		}
		
		if (inLeaders) {
			for (int j=9; num > j; j--) {
				scores[j] = scores[j - 1];
				names[j] = names[j - 1];
			}
			scores[num] = score;
			names[num] = name;
		}
		
	}
	
	public void sendLeaderboard(Player target) { //Cycle through the names table, pair it to score, send
		
	}
	
	
}
