package fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.teamevent.players;

import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import fr.warzoumc.skyexpander.teamcore.utils.GeneralPlayerInformation;
import fr.warzoumc.skyexpander.teamcore.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerTeamInformation {

    private Main main;
    private String senderName;
    private String targetName;

    private Team team;
    private Inventory inventory;
    private GeneralPlayerInformation generalPlayerInformation;
    private Player sender;

    private int senderGroupPriority;
    public PlayerTeamInformation(Main main, String senderName, String targetName){
        this.main = main;
        this.senderName = senderName;
        this.targetName = targetName;

        this.team = Team.fromPlayerName(main, senderName);
        this.generalPlayerInformation = new GeneralPlayerInformation(main, targetName);
        this.sender = Bukkit.getPlayer(senderName);

        if (team == null)
            return;
        this.senderGroupPriority = this.team.getGroupPriority(this.team.groupNameFromPlayer(sender));
        createInventory(9 * (this.senderGroupPriority > 2 ? 4 : 6));
    }

    private void createInventory(int size){
        Bukkit.broadcastMessage(this.team.getGroupPriority(this.team.groupNameFromPlayerName(targetName)) + "");
        this.inventory = Bukkit.createInventory(null, size, team.getColor() + team.getTeamName() +
                "§f, " + generalPlayerInformation.getDisplayName());
        sender.openInventory(inventory);
        contribution();
        information();
        operatorGestion();
    }

    private void contribution(){
        new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§bContribution")
                .addEnchantment(Enchantment.DURABILITY, 1, true)
                .hideEnchante()
                .inject(inventory, 15);
    }

    private void information(){
        new ItemBuilder(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal()).setName("§6Information")
                .setSkullTextureFromePlayerName(targetName)
                .inject(inventory, 11);
    }

    private void operatorGestion(){
        if (this.senderGroupPriority > 2)
            return;
        new ItemBuilder(Material.COMPASS).setName("§eModification des propriétés du joueur")
                .inject(inventory, 31);
    }

}
