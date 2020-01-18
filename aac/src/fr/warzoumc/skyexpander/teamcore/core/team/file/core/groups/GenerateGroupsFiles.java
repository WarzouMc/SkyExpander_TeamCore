package fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups;

import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.list.TeamGroupPlayerList;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.permission.TeamGroupPermission;
import fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups.propertys.TeamGroupProperty;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;

public class GenerateGroupsFiles {

    private Main main;
    private File groupsFile;
    private Player creator;

    public GenerateGroupsFiles(Main main, File groupsFile, Player creator) {
        this.main = main;
        this.groupsFile = groupsFile;
        this.creator = creator;
    }

    public void generate(){
        for (String s : new TeamGroupList(main, groupsFile).getGroups()) {
            String filePath = groupsFile.getPath() + "/" + s;
            File file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
                if (new TeamGroupList(main, groupsFile).getGroups().get(0).equalsIgnoreCase(s)){
                    new TeamGroupPlayerList(main, file, creator);
                } else {
                    new TeamGroupPlayerList(main, file);
                }
                new TeamGroupPermission(main, file, s);
                new TeamGroupProperty(main, file, s);
            }
        }
    }

}
