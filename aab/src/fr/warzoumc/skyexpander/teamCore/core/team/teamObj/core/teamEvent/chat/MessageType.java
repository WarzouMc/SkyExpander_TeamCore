package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.chat;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import fr.warzoumc.skyexpander.teamCore.utils.GeneralPlayerInformation;
import org.bukkit.entity.Player;

public class MessageType {

    public static final INFO INFO = new INFO();
    public static final PLAYER_MESSAGE PLAYER_MESSAGE = new PLAYER_MESSAGE();

    public static class INFO {
        private String information;
        private Team team;
        private int importance;

        public INFO setTeam(Team team){
            this.team = team;
            return this;
        }

        public INFO setImportance(int importance){
            this.importance = importance;
            return this;
        }

        public INFO setInfo(String info){
            this.information = info;
            return this;
        }

        public String build(){
            return buildInfo() + " §2>> §r" + information;
        }

        private String[] getImportanceColor(){
            return importance == 0 ? new String[] {"§1", "§9"} :
                    importance == 1 ? new String[] {"§2", "§a"} : new String[] {"§4", "§c"};
        }

        private String buildInfo(){
            return team.getColor() + "[§f" + team.getTeamName() + team.getColor() + "] " + getImportanceColor()[0] + "[" + getImportanceColor()[1] + "Info" + getImportanceColor()[0] + "]";
        }
    }

    public static class PLAYER_MESSAGE {
        private String message;
        private Team team;
        private Player player;

        public PLAYER_MESSAGE setTeam(Team team){
            this.team = team;
            return this;
        }

        public PLAYER_MESSAGE setPlayer(Player player){
            this.player = player;
            return this;
        }

        public PLAYER_MESSAGE setMessage(String message){
            this.message = message;
            return this;
        }

        public String build(Main main){
            return buildInfo(main) + " §2>> §r" + message;
        }

        private String buildInfo(Main main){
            GeneralPlayerInformation generalPlayerInformation = new GeneralPlayerInformation(main, player.getName());
            return team.getColor() + "[§f" + team.getTeamName() + team.getColor() + "] " + team.getGroupDef(player)
                    + " " + generalPlayerInformation.getDisplayName();
        }
    }

}
