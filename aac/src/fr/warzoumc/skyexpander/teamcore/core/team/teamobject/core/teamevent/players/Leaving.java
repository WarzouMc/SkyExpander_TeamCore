package fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.teamevent.players;

import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.players.GeneratePlayersFiles;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.teamevent.team.Disband;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.stream.Collectors;

public class Leaving {

    private Main main;
    private Player player;

    public Leaving(Main main, Player player) {
        this.main = main;
        this.player = player;
        leave();
    }

    private void leave(){
        if (Team.fromPlayer(main, player) != null){
            for (Entity entity : player.getLocation().getWorld().getEntities().stream().filter(entity -> entity instanceof ArmorStand).collect(Collectors.toList())) {
                if (entity.hasMetadata("team." + player.getName() + ".0") || entity.hasMetadata("team." + player.getName() + ".1")){
                    entity.remove();
                }
            }
            String teamName = Objects.requireNonNull(Team.fromPlayer(main, player)).getTeamName();
            Team team = Team.fromPlayer(main, player);
            if (team.getTeamSize() == 1){
                new Disband(main, team);
            } else {
                new GeneratePlayersFiles(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/players")).degenerate(main, player);
                new TeamGroupPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups/" + team.groupNameFromPlayer(player)), player, 1);
                new TeamPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/players"), player, 1);
            }
        } else player.sendMessage(main.getSuffix()[1] + "Tu dois leave ta team pour en rejoindre une autre !");
    }
}
