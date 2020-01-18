package fr.warzoumc.skyexpander.teamcore.core.team.file.core.players;

import fr.warzoumc.skyexpander.teamcore.core.team.file.core.players.player.TeamPlayerStat;
import fr.warzoumc.skyexpander.teamcore.core.team.teamobject.Team;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import org.bukkit.entity.Player;

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

    public void degenerate(Main main, Player player) {
        String filePath = main.getDataFolder() + "/team/" + Team.fromPlayer(main, player).getTeamName() + "/core/players/" + player.getName();
        File file = new File(filePath);
        for (File f : file.listFiles()) {
            f.delete();
        }
        file.delete();
    }
}
