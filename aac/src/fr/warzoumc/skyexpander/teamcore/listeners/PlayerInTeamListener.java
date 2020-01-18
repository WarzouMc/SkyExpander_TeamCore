package fr.warzoumc.skyexpander.teamcore.listeners;

import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.teamevent.chat.MessageType;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.teamevent.chat.TeamChat;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import fr.warzoumc.skyexpander.teamcore.utils.GeneralPlayerInformation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInTeamListener implements Listener {

    private Main main;
    public PlayerInTeamListener(Main main) {this.main = main;}

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (inTeam(player)){
            GeneralPlayerInformation generalPlayerInformation = new GeneralPlayerInformation(main, player.getName());
            String message = MessageType.INFO.setImportance(0).setTeam(Team.fromPlayer(main, player))
                    .setInfo(generalPlayerInformation.getDisplayName() + " §aà rejoind !").build();
            TeamChat.send(Team.fromPlayer(main, player), message);
        }
    }

    @EventHandler
    public void onIventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();
        String inventoryName = inventory.getName();
        if (!inventoryName.startsWith("")){

        }
    }

    private boolean inTeam(Player player){
        return Team.fromPlayer(main, player) != null;
    }
}
