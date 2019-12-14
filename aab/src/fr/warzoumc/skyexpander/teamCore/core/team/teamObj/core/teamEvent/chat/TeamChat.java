package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.chat;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;

public class TeamChat {

    public static void send(Team team, String message){
        if (team == null) return;
        team.getOnlinePlayer().forEach(player -> player.sendMessage(message));
    }

}
