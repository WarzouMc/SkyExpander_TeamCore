package fr.warzoumc.skyexpander.teamCore.core.players;

import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GeneralPlayerInformation {

    private Main main;
    private String playerName;
    private String filePath;
    private File mainFile;
    private FileConfiguration mainConfig;
    public GeneralPlayerInformation(Main main, String playerName){
        this.main = main;
        this.playerName = playerName;
        this.filePath = "plugins/SkyExpanderInternalPlugin/config.yml";
        this.mainFile = new File(filePath);
        if (existMainFile()){
            this.mainConfig = YamlConfiguration.loadConfiguration(mainFile);
        }
    }

    private boolean existMainFile(){
        return mainFile.exists();
    }

    public int getMoney(){
        return existMainFile() && mainConfig.contains("monai." + playerName) ? mainConfig.getInt("monai." +
                playerName) : 0;
    }

    public void rmvMoney(int i){
        if (existMainFile() && mainConfig.contains("monai." + playerName)){
            mainConfig.set("monai." + playerName, getMoney() - i);
            try {
                mainConfig.save(mainFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            YamlConfiguration.loadConfiguration(mainFile);
        }
    }

}
