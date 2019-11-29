package fr.warzoumc.skyexpander.teamCore.core.team.file.core.players.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class TeamPlayerStat {

    private Main main;
    private File playerFile;
    private String player;

    private String statsPath;
    private File statsFile;

    private JSONObject jsonObject;
    private JSONParser jsonParser = new JSONParser();

    private HashMap<String, Object> defaults = new HashMap<>();

    public TeamPlayerStat(Main main, File playerFile, String player) {
        this.main = main;
        this.playerFile = playerFile;
        this.player = player;

        this.statsPath = playerFile.getPath() + "/stats.json";
        this.statsFile = new File(statsPath);

        reload();
        //action
        save();
    }

    private void reload() {
        if (!statsFile.getParentFile().exists()){
            statsFile.getParentFile().mkdirs();
        }
        try {
            if (!statsFile.exists()){
                PrintWriter printWriter = new PrintWriter(statsFile, "UTF-8");
                printWriter.print("{");
                printWriter.print("}");
                printWriter.flush();
                printWriter.close();
                defaultsStats();
            }
            this.jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(statsFile), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void defaultsStats() {
        JSONObject timeObj = new JSONObject();
        timeObj.put("join", new Date().getTime());
        timeObj.put("totalPlayTimeInTeam", 0);
        timeObj.put("offlineSince", 0);
        timeObj.put("onlineSince", 0);

        JSONObject teamContribution = new JSONObject();
        teamContribution.put("timePoints", 0);
        teamContribution.put("messageSend", 0);
        teamContribution.put("personnalBoostSendAtTeam", 0);

        defaults.put("timeObj", timeObj);
        defaults.put("teamContribution", teamContribution);
    }

    @SuppressWarnings("unchecked")
    private void save(){
        try {
            JSONObject toSave = jsonObject;

            for (String string : defaults.keySet()){
                Object o = defaults.get(string);
                if (o instanceof JSONObject){
                    toSave.put(string, getObject(string));
                } else if (o instanceof Integer){
                    toSave.put(string, getInteger(string));
                } else if (o instanceof JSONArray){
                    toSave.put(string, getArray(string));
                }
            }

            TreeMap<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            treeMap.putAll(toSave);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String pettryJsonString = gson.toJson(treeMap);

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.statsFile), "UTF-8"));
            fileWriter.write(pettryJsonString);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRawData(String key) {
        return defaults.containsKey(key) ? defaults.get(key).toString()
                : (jsonObject.containsKey(key) ? jsonObject.get(key).toString() : key);
    }

    private String getString(String key) {
        return ChatColor.translateAlternateColorCodes('&', getRawData(key));
    }

    private double getInteger(String key) {
        return Double.parseDouble(getRawData(key));
    }

    private JSONObject getObject(String key) {
        return defaults.containsKey(key) ? (JSONObject) defaults.get(key)
                : (jsonObject.containsKey(key) ? (JSONObject) jsonObject.get(key) : new JSONObject());
    }

    private JSONArray getArray(String key) {
        return defaults.containsKey(key) ? (JSONArray) defaults.get(key)
                : (jsonObject.containsKey(key) ? (JSONArray) jsonObject.get(key) : new JSONArray());
    }

}
