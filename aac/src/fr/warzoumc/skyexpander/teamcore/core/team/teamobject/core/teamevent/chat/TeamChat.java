package fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.teamevent.chat;

import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;

public class TeamChat {

    public static void send(Team team, String message){
        if (team == null) return;
        team.getOnlinePlayer().forEach(player -> player.sendMessage(message));
    }

}
