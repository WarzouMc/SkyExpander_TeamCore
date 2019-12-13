package fr.warzoumc.skyexpander.teamCore.utils;

import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GeneralPlayerInformation {

    private Main main;
    private String playerName;
    private String moneyPath;
    private File moneyFile;
    private FileConfiguration moneyConfig;

    private String permanentUpgradePath;
    private File permanentUpgradeFile;
    private FileConfiguration permanentUpgradeConfig;

    private String permPath;
    private File permFile;
    private FileConfiguration permConfig;
    public GeneralPlayerInformation(Main main, String playerName){
        this.main = main;
        this.playerName = playerName;
        this.moneyPath = "plugins/SkyExpanderInternalPlugin/money.yml";
        this.moneyFile = new File(moneyPath);
        if (existMoneyFile()){
            this.moneyConfig = YamlConfiguration.loadConfiguration(moneyFile);
        }

        this.permanentUpgradePath = "plugins/SkyExpanderInternalPlugin/permanentConfig.yml";
        this.permanentUpgradeFile = new File(permanentUpgradePath);
        if (existPermanentUpgradeFile()){
            this.permanentUpgradeConfig = YamlConfiguration.loadConfiguration(permanentUpgradeFile);
        }

        this.permPath = "plugins/SkyExpanderInternalPlugin/perm.yml";
        this.permFile = new File(permPath);
        if (existPermFile()){
            this.permConfig = YamlConfiguration.loadConfiguration(permFile);
        }
    }

    private boolean existMoneyFile(){
        return moneyFile.exists();
    }

    private boolean existPermanentUpgradeFile(){
        return permanentUpgradeFile.exists();
    }

    private boolean existPermFile(){
        return permFile.exists();
    }

    public boolean isOnline(){
        return Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(playerName));
    }

    public int getMoney(){
        return existMoneyFile() && moneyConfig.contains("money." + playerName) ? moneyConfig.getInt("money." +
                playerName) : 0;
    }

    public void rmvMoney(int i){
        if (existMoneyFile() && moneyConfig.contains("money." + playerName)){
            moneyConfig.set("money." + playerName, getMoney() - i);
            try {
                moneyConfig.save(moneyFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            YamlConfiguration.loadConfiguration(moneyFile);
        }
    }

    public int getDonationLevel(){
        return existPermanentUpgradeFile() && permanentUpgradeConfig.contains(playerName + ".donation") ?
                permanentUpgradeConfig.getInt(playerName + ".donation") : 0;
    }

    public String getDisplayName(){
        return existPermFile() && permConfig.contains(playerName + ".DisplayName") ?
                permConfig.getString(playerName + ".DisplayName") : playerName;
    }

}
