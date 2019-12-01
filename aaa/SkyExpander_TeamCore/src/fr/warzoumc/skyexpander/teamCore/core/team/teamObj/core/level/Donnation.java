package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level;

import fr.warzoumc.skyexpander.teamCore.core.players.GeneralPlayerInformation;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Donnation {

    private Main main;
    private Player donates;

    private Team team;

    public Donnation(Main main, Player donates) {
        this.main = main;
        this.donates = donates;
        this.team = Team.fromPlayer(main, donates);
        if (team == null) return;
        donates.sendMessage(team.getTeamName() + " : " + new GeneralPlayerInformation(main, donates.getName()).getMoney());
    }
}
