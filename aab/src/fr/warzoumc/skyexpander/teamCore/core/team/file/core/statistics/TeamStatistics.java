package fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.boost.TeamBoost;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.level.TeamLevelStat;
import fr.warzoumc.skyexpander.teamCore.main.Main;

import java.io.File;

public class TeamStatistics {

    private Main main;
    private File coreFile;

    private String statsPath;
    private File statsFile;

    public TeamStatistics(Main main, File coreFile) {
        this.main = main;
        this.coreFile = coreFile;

        this.statsPath = coreFile.getPath() + "/stats";
        this.statsFile = new File(statsPath);
    }

    public void create(){
        statsFile.mkdirs();
        new TeamLevelStat(main, statsFile);
        new TeamBoost(main, statsFile);
    }

}
