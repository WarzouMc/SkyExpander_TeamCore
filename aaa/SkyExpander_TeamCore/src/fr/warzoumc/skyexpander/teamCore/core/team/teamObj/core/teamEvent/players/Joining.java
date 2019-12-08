package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.players;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.TeamGroupList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.GeneratePlayersFiles;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class Joining {

    private Main main;
    private Player player;
    private String teamName;

    public Joining(Main main, Player player, String teamName) {
        this.main = main;
        this.player = player;
        this.teamName = teamName;
        join();
    }

    private void join(){
        if (Team.exist(main, teamName)){
            if (Team.fromPlayer(main, player) == null){
                new TeamPlayerList(main, new File(main.getDataFolder() + "/team/" +teamName + "/core/players"), player, 0);
                new GeneratePlayersFiles(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/players")).generate();
                List<String> groups =new TeamGroupList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups")).getGroups();
                new TeamGroupPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups/" + groups.get(groups.size() - 1)), player, 0);
            } else player.sendMessage(main.getSuffix()[1] + "Tu dois leave ta team pour en rejoindre une autre !");
        } else player.sendMessage(main.getSuffix()[1] + "Cette team n'existe pas !");
    }
}
