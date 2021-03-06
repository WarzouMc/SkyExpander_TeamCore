package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;

import java.io.File;

public class PassLevel {

    private Main main;
    private Team team;

    public PassLevel(Main main, Team team) {
        this.main = main;
        this.team = team;
    }

    private boolean canPass(){
        return team.getTeamLevelNeedPoint() - team.getTeamTotalPoint() <= 0;
    }

    public void pass(){
        if (canPass()){
            int teamLevel = team.getTeamLevel() + 1;
            double newNeedPointD = ((teamLevel * (teamLevel - 1)) + (teamLevel * (1 + (teamLevel / 200)))) * 1000;
            int newNeedPoint = (int) Math.round(newNeedPointD);
            TeamLevelStat teamLevelStat = new TeamLevelStat(main, new File(main.getDataFolder() + "/team/" + team.getTeamName() + "/core/stats"), true);
            teamLevelStat.addLevel(1);
            teamLevelStat.setNeedPoints(newNeedPoint);
            teamLevelStat.save();
        }
    }
}
