package io.github.willywonka125.swegpoints;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;


public class Leaderboard {
	
	int[] scores = new int[10];
	String[] names = new String[10];
	int max = 0;
	int min = 0;
	
	Plugin main = SwegPoints.getPlugin();
	
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
	
	public String[] getLeaderboard() { //Cycle through the names table, pair it to score, send
		
		String[] compiled = new String[10];
		
		for (int i=9; i>=0; i--) {
			if (!names[i].equals(null)) {
			compiled[i] = ChatColor.GRAY + "" + i+1 + ": " + ChatColor.GOLD + names[i] + " - " + scores[i];
			} else {
				break;
			}
		}
		return compiled;
	}
}
