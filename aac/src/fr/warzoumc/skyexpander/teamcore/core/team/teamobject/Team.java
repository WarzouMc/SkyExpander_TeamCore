package fr.warzoumc.skyexpander.teamcore.core.team.teamobject;

import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.TeamGroupList;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.propertys.TeamGroupProperty;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.players.TeamPlayerList;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import fr.warzoumc.skyexpander.teamcore.utils.GeneralPlayerInformation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

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
        return fromPlayerName(var0, var1.getName());
    }

    public static Team fromPlayerName(Main var0, String var1){
        for (File files : Objects.requireNonNull(new File(var0.getDataFolder() + "/team").listFiles())) {
            String playersPath = files.getPath() + "/core/players";
            List<String> playerList = new TeamPlayerList(var0, new File(playersPath)).getPlayers();
            if (playerList.contains(var1)){
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

    /*team player*/
    public List<String> getTeamPlayerList(){
        TeamPlayerList teamPlayerList = new TeamPlayerList(main, new File(main.getDataFolder() + "/team/"
                + teamName + "/core/players"));
        return teamPlayerList.getPlayers();
    }

    public List<Player> getTeamPlayerList_(){
        List<Player> playerList = new ArrayList<>();
        getTeamPlayerList().forEach(s -> playerList.add(Bukkit.getPlayer(s)));
        return playerList;
    }

    public List<Player> getOnlinePlayer(){
        return getTeamPlayerList_().stream().filter(player -> Bukkit.getOnlinePlayers().contains(player))
                .collect(Collectors.toList());
    }

    public List<Player> getOfflinePlayer(){
        return getTeamPlayerList_().stream().filter(player -> !Bukkit.getOnlinePlayers().contains(player))
                .collect(Collectors.toList());
    }

    public List<String> getOnlinePlayerName(){
        List<String> playerList = new ArrayList<>();
        getOnlinePlayer().forEach(player -> playerList.add(player.getName()));
        return playerList;
    }

    public List<String> getOffLinePlayerName(){
        List<String> playerList = new ArrayList<>();
        getOfflinePlayer().forEach(player -> playerList.add(player.getName()));
        return playerList;
    }

    /*team stat*/
    public int getTeamLevel(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/"
                + teamName + "/core/stats"));
        return teamLevelStat.getLevel();
    }

    public int getTeamLevelNeedMoney(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/"
                + teamName + "/core/stats"));
        return teamLevelStat.getNeedMoney();
    }

    public int getTeamTotalMoney(){
        TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/"
                + teamName + "/core/stats"));
        return teamLevelStat.getTotalMoney();
    }

    public int getTeamSize(){
        List<String> playerList = new TeamPlayerList(main, new File(main.getDataFolder() + "/team/"
                + teamName + "/core/players")).getPlayers();
        return playerList.size();
    }

    /*Team groups*/
    public String groupNameFromPlayer(Player player){
        for (String groups : getGroups()) {
            TeamGroupPlayerList teamGroupPlayerList = new TeamGroupPlayerList(main,
                    new File(main.getDataFolder()
                    + "/team/" + teamName + "/core/groups/" + groups), player, "").action(-1);
            if (teamGroupPlayerList.getPlayers().contains(player.getName())){
                return groups;
            }
        }
        return null;
    }

    public String groupNameFromPlayerName(String player){
        for (String groups : getGroups()) {
            TeamGroupPlayerList teamGroupPlayerList = new TeamGroupPlayerList(main,
                    new File(main.getDataFolder()
                            + "/team/" + teamName + "/core/groups/" + groups), null, "").action(-1);
            if (teamGroupPlayerList.getPlayers().contains(player)){
                return groups;
            }
        }
        return null;
    }

    public List<String> getGroupPlayerList(String groups){
        TeamGroupPlayerList teamGroupPlayerList = new TeamGroupPlayerList(main, new File(main.getDataFolder()
                + "/team/" + teamName + "/core/groups/" + groups), null, "").action(-1);
        return teamGroupPlayerList.getPlayers();
    }

    public List<String> getGroups(){
        TeamGroupList teamGroupList = new TeamGroupList(main, new File(main.getDataFolder() + "/team/"
                + teamName + "/core/groups"));
        return teamGroupList.getGroups();
    }

    //Graphics information
    public String getGroupDef(Player player){
        return getGroupDef(groupNameFromPlayer(player));
    }

    public String getGroupDef(String group){
        TeamGroupProperty teamGroupProperty = new TeamGroupProperty(main, new File(main.getDataFolder()
                + "/team/" + teamName + "/core/groups/" + group), group, "").action(-1);
        return teamGroupProperty.getGroupDef();
    }

    public int getGroupPriority(String group){
        return getGroups().indexOf(group);
    }

    public String getPlayersList(){
        String teamColor = main.colorFromLevel(getTeamLevel());
        StringBuilder string = new StringBuilder(teamColor + "####§2 " + teamName + teamColor + " ####§r\n");
        Map<String, List<String>> groupOfPlayer = new HashMap<>();
        for (String groups : getGroups()) {
            groupOfPlayer.put(groups, getGroupPlayerList(groups));
        }
        groupOfPlayer.forEach((s, strings) -> {
            if (strings.size() > 0){
                string.append(getGroupDef(s) + "§f :  \n");
                strings.forEach(playerName -> {
                    GeneralPlayerInformation generalPlayerInformation = new GeneralPlayerInformation(main, playerName);
                    string.append(generalPlayerInformation.getDisplayName()).append((generalPlayerInformation.isOnline()
                            ? "§2" : "§4") + " \u25CF§r").append(", ");
                });
                string.delete(string.length() - 2, string.length());
                string.append("\n");
            }
        });

        return string.toString();
    }

    public String getProgression(){
        String teamColor = main.colorFromLevel(getTeamLevel());
        int inMore = 0;
        if (getTeamTotalMoney() > getTeamLevelNeedMoney()){
            inMore = getTeamTotalMoney() - getTeamLevelNeedMoney();
        }
        return teamColor + "####§2 " + teamName + teamColor + " ####\n" +
                teamColor + "## §eProgression §f:\n" +
                teamColor + "## §aLevel §f: §9" + getTeamLevel() + "\n" +
                teamColor + "## §aArgent recueilli §f: §9" + getTeamTotalMoney() + "§f/§1" + getTeamLevelNeedMoney()
                + "\n" +
                teamColor + "## §aSurplus §f: §9" + inMore + "\n" +
                teamColor + "## §r" + getProgressionString();
    }

    public String getProgressionString(){
        String teamColor = main.colorFromLevel(getTeamLevel());
        int level = (int) Math.floor(Math.min((getTeamTotalMoney() + 0.0) / getTeamLevelNeedMoney() * 10.0, 10.0));
        StringBuilder str = new StringBuilder(teamColor + "§l[§r§2");
        for (int i = 0; i < level; i++) {
            str.append("\u2588");
        }
        str.append("§4");
        for (int i = 0; i < 10 - level; i++) {
            str.append("\u2588");
        }
        return String.valueOf(str.append(teamColor + "§l]§r"));
    }

    public String getColor(){
        return main.colorFromLevel(getTeamLevel());
    }
}
