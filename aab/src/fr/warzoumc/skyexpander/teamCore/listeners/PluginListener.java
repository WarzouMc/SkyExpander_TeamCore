package fr.warzoumc.skyexpander.teamCore.listeners;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.stream.Collectors;

public class PluginListener implements Listener {

    private Main main;
    public PluginListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        for (Entity entity : player.getLocation().getWorld().getEntitiesByClass(ArmorStand.class).stream().filter(ArmorStand::isMarker).collect(Collectors.toList())) {
            if (entity.hasMetadata("team." + player.getName() + ".0") || entity.hasMetadata("team." + player.getName() + ".1")){
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onUnloadChunk(ChunkUnloadEvent event){
        for (Player player : Bukkit.getOnlinePlayers().stream().filter(player -> Team.fromPlayer(main, player) != null)
                .collect(Collectors.toList())) {
            for (Entity entity : event.getWorld().getEntitiesByClass(ArmorStand.class).stream()
                    .filter(ArmorStand::isMarker).filter(armorStand -> armorStand.getLocation().getChunk() ==
                            event.getChunk()).collect(Collectors.toList())) {
                if (entity.hasMetadata("team." + player.getName() + ".0") || entity.hasMetadata("team." +
                        player.getName() + ".1")){
                    entity.remove();
                }
            }
        }
    }

}
