package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level;

import fr.warzoumc.skyexpander.teamCore.core.players.GeneralPlayerInformation;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Donnation {

    private Main main;
    private Player donates;

    private Team team;

    public Donnation(Main main, Player donates) {
        this.main = main;
        this.donates = donates;
        this.team = Team.fromPlayer(main, donates);
        if (team != null){
            donate();
        }
    }

    private void donate() {
        GeneralPlayerInformation generalPlayerInformation = new GeneralPlayerInformation(main, donates.getName());
        int donation = main.donationPerLevel()[generalPlayerInformation.getDonationLevel()];
        if (generalPlayerInformation.getMoney() >= donation){
            generalPlayerInformation.rmvMoney(donation);
            TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" +
                    team.getTeamName() + "/core/stats"), true);
            teamLevelStat.addTotalPoint(donation);
            teamLevelStat.save();
        }
    }
}
