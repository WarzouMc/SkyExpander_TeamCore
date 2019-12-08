package fr.warzoumc.skyexpander.teamCore.core.team.teamObj;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.TeamGroupList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.propertys.TeamGroupProperty;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Team {

    private Main main;
    private String teamName;
    public Team(Main var1, String var2){
        this.main = var1;
        this.teamName = var2;
    }

    public static boolean exist(Main var0, String var1){
        for (File files : Objects.requireNonNull(new File(var0.getDataFolder() + "/team").listFiles())) {
            if (files.getName().equalsIgnoreCase(var1)){
                return true;
            }
        }
        return false;
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
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/stats"));
        return teamLevelStat.getLevel();
    }

    public int getTeamLevelNeedPoint(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/stats"));
        return teamLevelStat.getNeedPoints();
    }

    public int getTeamTotalPoint(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/stats"));
        return teamLevelStat.getTotalPoint();
    }

    public int getTeamSize(){
        List<String> playerList = new TeamPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/players")).getPlayers();
        return playerList.size();
    }

    /*Team groups*/
    public String groupNameFromPlayer(Player player){
        TeamGroupList teamGroupList = new TeamGroupList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups"));
        for (String groups : teamGroupList.getGroups()) {
            TeamGroupPlayerList teamGroupPlayerList = new TeamGroupPlayerList(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups/" + groups), player, -1);
            if (teamGroupPlayerList.getPlayers().contains(player.getName())){
                return groups;
            }
        }
        return null;
    }


    //Graphics information
    public String getGroupDef(Player player){
        TeamGroupProperty teamGroupProperty = new TeamGroupProperty(main, new File(main.getDataFolder() + "/team/" + teamName + "/core/groups/" + groupNameFromPlayer(player)), groupNameFromPlayer(player), -1);
        return teamGroupProperty.getGroupDef();
    }
}
