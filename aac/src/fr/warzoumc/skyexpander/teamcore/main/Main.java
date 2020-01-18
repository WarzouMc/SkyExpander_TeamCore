package fr.warzoumc.skyexpander.teamcore.main;

import fr.warzoumc.skyexpander.teamcore.core.commands.TeamChatCommand;
import fr.warzoumc.skyexpander.teamcore.core.commands.TeamCommand;
import fr.warzoumc.skyexpander.teamcore.listeners.PlayerInTeamListener;
import fr.warzoumc.skyexpander.teamcore.listeners.PluginListener;
import fr.warzoumc.skyexpander.teamcore.runnables.TeamInfoUpdater;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //create base file
        if (!new File(getDataFolder() + "/team").exists()){
            new File(getDataFolder() + "/team").mkdirs();
        }

        //Commands
        getCommand("team").setExecutor(new TeamCommand(this));
        getCommand("tc").setExecutor(new TeamChatCommand(this));

        //Listeners
        getServer().getPluginManager().registerEvents(new PluginListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInTeamListener(this), this);

        //runnable
        TeamInfoUpdater teamInfoUpdater = new TeamInfoUpdater(this);
        teamInfoUpdater.runTaskTimer(this, 0, 1);
    }

    private String[] suffix = new String[] {"§1[§9Team§1] §2>> §1", "§4[§cTeam§4] §2>> §4"};

    public String[] getSuffix() {
        return suffix;
    }

    public int[] donationPerLevel(){
        return new int[] {0,100,250,500,750,1000,1500,2000,3000,5000,7500,10000,12500,15000,20000,50000,75000,100000,
                250000,500000,750000,1000000,1250000,1500000,1750000,2000000,2250000,2500000,2750000,3000000,3500000};
    }

    public long giveMeTime() throws ParseException {
        Date date = new Date(new Date().getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        String dateString = simpleDateFormat.format(date);
        return simpleDateFormat.parse(dateString).getTime();
    }

    public String colorFromLevel(int level){
        String[] colors = new String[] {"§7", "§f", "§9", "§1", "§a", "§e", "§6", "§c", "§4"};
        level = level / 20;
        return colors[level];
    }

}
