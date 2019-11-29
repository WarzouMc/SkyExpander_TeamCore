package fr.warzoumc.skyexpander.teamCore.core.team.teamObj;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Team {

    private Main main;
    private String teamName;
    public Team(Main var1, String var2){
        this.main = var1;
        this.teamName = var2;
    }

    /*static team from a team var*/

    public static Team fromPlayer(Main var0, Player var1){
        for (File files : Objects.requireNonNull(new File(var0.getDataFolder() + "/team").listFiles())) {
            String playersPath = files.getPath() + "/core/players";
            List<String> playerList = new TeamPlayerList(var0, new File(playersPath)).getPlayers();
            if (playerList.contains(var1.getName())){
                return new Team(var0, files.getName());
            }
        }
        return null;
    }

    // team vars
    /*Basic vars*/

    public String getTeamName(){
        return teamName;
    }

    /*team stat*/
    public int getTeamLevel(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(teamName + "/core/stats"));
        return teamLevelStat.getLevel();
    }

    public int getTeamLevelNeedPoint(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(teamName + "/core/stats"));
        return teamLevelStat.getNeedPoints();
    }



}
