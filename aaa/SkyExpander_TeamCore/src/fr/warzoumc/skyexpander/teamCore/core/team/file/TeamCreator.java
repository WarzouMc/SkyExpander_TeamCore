package fr.warzoumc.skyexpander.teamCore.core.team.file;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.TeamCore;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.Normalizer;

public class TeamCreator {

    private Main main;
    private String teamName;
    private Player creator;

    private String path;
    private File teamFile;

    public TeamCreator(Main main, String teamName, Player creator) {
        this.main = main;
        this.teamName = unaccent(teamName);
        this.creator = creator;

        this.path = main.getDataFolder() + "/team/" + this.teamName;
        this.teamFile = new File(path);
    }

    public void createTeam(){
        if (!teamFile.exists()){
            teamFile.mkdirs();
            new TeamCore(main, teamFile, creator).create();
            return;
        }
        creator.sendMessage(main.getSuffix()[1] + "Ce nom est déjà pris !");
    }


    private static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
