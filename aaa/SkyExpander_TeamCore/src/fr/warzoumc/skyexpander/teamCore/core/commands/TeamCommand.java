package fr.warzoumc.skyexpander.teamCore.core.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import fr.warzoumc.skyexpander.teamCore.core.team.file.TeamCreator;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.TeamGroupList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.GeneratePlayersFiles;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level.Donnation;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.players.Joining;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.players.Leaving;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class TeamCommand implements CommandExecutor {

    private Main main;
    public TeamCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 2){
                if (args[0].equalsIgnoreCase("create")){
                    new TeamCreator(main, args[1], player).createTeam();
                } else if (args[0].equalsIgnoreCase("join")){
                    new Joining(main, player, args[1]);
                } else if (args[0].equalsIgnoreCase("leave")) {
                    new Leaving(main, player);
                }
            } else {
                new Donnation(main, player);
            }
        }
        return false;
    }
}
