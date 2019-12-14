package fr.warzoumc.skyexpander.teamCore.listeners;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.chat.MessageType;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.chat.TeamChat;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import fr.warzoumc.skyexpander.teamCore.utils.GeneralPlayerInformation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerInTeamListener implements Listener {

    private Main main;
    public PlayerInTeamListener(Main main) {this.main = main;}

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (inTeam(player)){
            GeneralPlayerInformation generalPlayerInformation = new GeneralPlayerInformation(main, player.getName());
            String message = MessageType.INFO.setImportance(0).setTeam(Team.fromPlayer(main, player))
                    .setInfo(generalPlayerInformation.getDisplayName() + " §aà rejoint !").build();
            TeamChat.send(Team.fromPlayer(main, player), message);
        }
    }


    private boolean inTeam(Player player){
        return Team.fromPlayer(main, player) != null;
    }
}
