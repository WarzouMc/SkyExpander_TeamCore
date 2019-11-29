package fr.warzoumc.skyexpander.teamCore.core.team.file.core.players;

import fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.player.TeamPlayerStat;
import fr.warzoumc.skyexpander.teamCore.main.Main;

import java.io.File;

public class GeneratePlayersFiles {

    private Main main;
    private File playersFile;

    public GeneratePlayersFiles(Main main, File playersFile) {
        this.main = main;
        this.playersFile = playersFile;
    }

    public void generate(){
        for (String s : new TeamPlayerList(main, playersFile).getPlayers()) {
            String filePath = playersFile.getPath() + "/" + s;
            File file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
                new TeamPlayerStat(main, file, s);
            }
        }
    }

}
