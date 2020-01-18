package fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.level;

import fr.warzoumc.skyexpander.teamcore.core.team.file.core.players.player.DonationObject;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.players.player.TeamPlayerStat;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import fr.warzoumc.skyexpander.teamcore.utils.GeneralPlayerInformation;
import org.bukkit.entity.Player;

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
        if (!new TeamPlayerStat(main, new File(main.getDataFolder() + "/team/" + team.getTeamName() + "/core/" +
                "players/" + donates.getName()), donates.getName(), null).canDonate()){
            donates.sendMessage(main.getSuffix()[1] + "Tu dois attendre demain pour fair un nouveau don !");
            return;
        }
        if (generalPlayerInformation.getMoney() >= donation){
            generalPlayerInformation.rmvMoney(donation);
            TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" +
                    team.getTeamName() + "/core/stats"), true);
            teamLevelStat.addTotalMoney(donation);
            teamLevelStat.save();
            new TeamPlayerStat(main, new File(main.getDataFolder() + "/team/" + team.getTeamName() + "/core/" +
                    "players/" + donates.getName()), donates.getName(), new DonationObject(donation));
        }
    }
}
