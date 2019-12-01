package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.players;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.TeamGroupList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.GeneratePlayersFiles;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Objects;

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
            String teamName = Objects.requireNonNull(Team.fromPlayer(main, player)).getTeamName();
            Team team = Team.fromPlayer(main, player);
            new GeneratePlayersFiles(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/players")).degenerate(main, player);
            new TeamGroupPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups/" + team.groupNameFromPlayer(player)), player, 1);
            new TeamPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/players"), player, 1);
        } else player.sendMessage(main.getSuffix()[1] + "Tu dois leave ta team pour en rejoindre une autre !");
    }
}
