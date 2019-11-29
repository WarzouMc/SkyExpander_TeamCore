package fr.warzoumc.skyexpander.teamCore.main;

import fr.warzoumc.skyexpander.teamCore.core.commands.TeamCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //Commands
        getCommand("team").setExecutor(new TeamCommand(this));
    }

    private String[] suffix = new String[] {"§1[§9Team§1] §2>> §1", "§4[§cTeam§4] §2>> §4"};

    public String[] getSuffix() {
        return suffix;
    }
}
