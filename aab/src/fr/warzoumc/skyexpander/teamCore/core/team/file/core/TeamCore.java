package fr.warzoumc.skyexpander.teamCore.core.team.file.core;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.TeamGroupList;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.TeamGroups;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.TeamPlayers;
import fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.TeamStatistics;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;

public class TeamCore {

    private Main main;
    private File teamFile;
    private Player creator;

    private String corePath;
    private File coreFile;

    public TeamCore(Main main, File teamFile, Player creator) {
        this.main = main;
        this.teamFile = teamFile;
        this.creator = creator;

        this.corePath = teamFile.getPath() + "/core";
        this.coreFile = new File(corePath);
    }

    public void create(){
        coreFile.mkdirs();
        new TeamGroups(main, coreFile, creator).create();
        new TeamPlayers(main, coreFile, creator).create();
        new TeamStatistics(main, coreFile).create();
    }
}
