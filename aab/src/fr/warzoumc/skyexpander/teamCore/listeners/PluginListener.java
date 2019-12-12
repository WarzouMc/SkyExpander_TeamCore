package fr.warzoumc.skyexpander.teamCore.listeners;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import java.util.stream.Collectors;

public class PluginListener implements Listener {

    private Main main;
    public PluginListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        for (Entity entity : player.getLocation().getWorld().getEntities().stream().filter(entity -> entity instanceof ArmorStand).collect(Collectors.toList())) {
            if (entity.hasMetadata("team." + player.getName() + ".0") || entity.hasMetadata("team." + player.getName() + ".1")){
                entity.remove();
            }
        }
    }
}
