package io.github.willywonka125.zaddycraft.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import io.github.willywonka125.zaddycraft.ZaddyCraft;

/**
 * A class for creating and modifying YML files.
 * @author Will
 *@since Dev-0.1
 */
public class AbstractFile {

	protected ZaddyCraft zc;
	private File file;
	protected FileConfiguration config;
	private String filename;
	
	public AbstractFile(ZaddyCraft zaddy, String filename) {
		this.zc = zaddy;
		this.file = new File(zc.getDataFolder(), filename);
		this.filename = filename;
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.config = YamlConfiguration.loadConfiguration(file);
	} 
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reload() { //Replaces the file in memory with the one on disk
		File tmp = new File(zc.getDataFolder(), filename);
		try {
			if (!tmp.exists()) {
				zc.getLogger().info("File " + filename + " not found. Will save the one in memory to disk.");
				save();
			} else {
				file = tmp;
				this.config = YamlConfiguration.loadConfiguration(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
