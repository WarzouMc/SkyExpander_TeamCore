package fr.warzoumc.skyexpander.teamCore.runnables;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import fr.warzoumc.skyexpander.teamCore.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class TeamInfoUpdater extends BukkitRunnable {

    private Main main;

    public TeamInfoUpdater(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers().stream().filter(player -> Team.fromPlayer(main, player) != null).collect(Collectors.toList())) {
            boolean[] hasEntity = {false};
            Bukkit.getWorlds().forEach(world -> {
                for (Entity entity : world.getEntitiesByClass(ArmorStand.class).stream().filter(ArmorStand::isMarker).collect(Collectors.toList())) {
                    if (entity.hasMetadata("team." + player.getName() + ".0")){
                        if (player.isSneaking()){
                            entity.remove();
                            hasEntity[0] = true;
                        } else {
                            Location location = player.getLocation();
                            location.setY(location.getY() + 2.4);
                            entity.teleport(location);
                            hasEntity[0] = true;
                        }
                    } else if (entity.hasMetadata("team." + player.getName() + ".1")){
                        if (player.isSneaking()){
                            entity.remove();
                        } else {
                            Location location = player.getLocation();
                            location.setY(location.getY() + 2.7);
                            entity.teleport(location);
                        }
                    }
                }
            });
            if (!hasEntity[0] && !player.isSneaking()){
                Team team = Team.fromPlayer(main, player);
                String teamColor = main.colorFromLevel(team.getTeamLevel());
                ArmorStand teamArmorStand = (ArmorStand) player.getLocation().getWorld()
                        .spawnEntity(player.getLocation().add(0, 2.4, 0), EntityType.ARMOR_STAND);
                teamArmorStand.setGravity(false);
                teamArmorStand.setCustomName(teamColor + "[§f" + team.getTeamName() + teamColor + "]§r §rlevel : " +
                        teamColor + team.getTeamLevel());
                teamArmorStand.setCustomNameVisible(true);
                teamArmorStand.setMarker(true);
                teamArmorStand.setVisible(false);
                teamArmorStand.setHelmet(new ItemBuilder(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal())
                        .setSkullTexture(player).toItemStack());
                teamArmorStand.setMetadata("team." + player.getName() + ".0",
                        new FixedMetadataValue(main, "team." + player.getName() + ".0"));

                ArmorStand teamGroupArmorStand = (ArmorStand) player.getLocation().getWorld()
                        .spawnEntity(player.getLocation().add(0, 2.7, 0), EntityType.ARMOR_STAND);
                teamGroupArmorStand.setGravity(false);
                teamGroupArmorStand.setCustomName(team.getGroupDef(player));
                teamGroupArmorStand.setCustomNameVisible(true);
                teamGroupArmorStand.setMarker(true);
                teamGroupArmorStand.setVisible(false);
                teamGroupArmorStand.setMetadata("team." + player.getName() + ".1", new FixedMetadataValue(main,
                        "team." + player.getName() + ".1"));
            }
        }
    }
}
