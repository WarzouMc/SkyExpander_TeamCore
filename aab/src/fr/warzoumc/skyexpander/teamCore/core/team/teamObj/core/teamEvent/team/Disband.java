package fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.team;

import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.sound.midi.Patch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Disband {

    private Main main;
    private Team team;

    public Disband(Main main, Team team) {
        this.main = main;
        this.team = team;
        try {
            disband();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disband() throws IOException {
        List<Path> listJson = Files.walk(Paths.get(main.getDataFolder() + "/team/" + team.getTeamName()), 10)
                .filter(path -> path.endsWith(".json"))
                .collect(Collectors.toList());
        listJson.forEach(path -> path.toFile().delete());

        List<Path> listDir = Files.walk(Paths.get(main.getDataFolder() + "/team/" + team.getTeamName()), 10)
                .collect(Collectors.toList());
        for (int i = listDir.size() -1; i > -1 ; i--) {
            listDir.get(i).toFile().delete();
        }
    }

}
