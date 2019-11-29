package fr.warzoumc.skyexpander.teamCore.core.team.file.core.players;

import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.entity.Player;

import java.io.File;

public class TeamPlayers {

    private Main main;
    private File coreFile;
    private Player creator;

    private String playersPath;
    private File playersFile;

    public TeamPlayers(Main main, File coreFile, Player creator) {
        this.main = main;
        this.coreFile = coreFile;
        this.creator = creator;

        this.playersPath = coreFile.getPath() + "/players";
        this.playersFile = new File(playersPath);
    }

    public void create(){
        playersFile.mkdirs();
        new TeamPlayerList(main, playersFile, creator);
        new GeneratePlayersFiles(main, playersFile).generate();
    }

}
