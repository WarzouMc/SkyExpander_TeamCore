package fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups;

import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;

public class TeamGroups {

    private Main main;
    private File coreFile;
    private Player creator;

    private String groupsPath;
    private File groupsFile;

    public TeamGroups(Main main, File coreFile, Player creator) {
        this.main = main;
        this.coreFile = coreFile;
        this.creator = creator;

        this.groupsPath = coreFile.getPath() + "/groups";
        this.groupsFile = new File(groupsPath);
    }

    public void create(){
        groupsFile.mkdirs();
        new TeamGroupList(main, groupsFile);
        new GenerateGroupsFiles(main, groupsFile, creator).generate();
    }

}
