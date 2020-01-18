package fr.warzoumc.skyexpander.teamcore.core.team.teamobject.core.level;

import fr.warzoumc.skyexpander.teamcore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;
import fr.warzoumc.skyexpander.teamcore.main.Main;

import java.io.File;

public class PassLevel {

    private Main main;
    private Team team;

    public PassLevel(Main main, Team team) {
        this.main = main;
        this.team = team;
        pass();
    }

    private boolean canPass(){
        return team.getTeamLevelNeedMoney() - team.getTeamTotalMoney() <= 0;
    }

    public void pass(){
        if (canPass()){
            int teamLevel = team.getTeamLevel() + 1;
            double newNeedPointD = ((teamLevel * (teamLevel - 1)) + (teamLevel * (1 + (teamLevel / 200)))) * 1000;
            int newNeedPoint = (int) Math.round(newNeedPointD);
            TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" + team.getTeamName() + "/core/stats"), true);
            teamLevelStat.rvmTotalMoney(teamLevelStat.getNeedMoney());
            teamLevelStat.addLevel(1);
            teamLevelStat.setNeedMoney(newNeedPoint);
            teamLevelStat.save();
        }
    }
}
