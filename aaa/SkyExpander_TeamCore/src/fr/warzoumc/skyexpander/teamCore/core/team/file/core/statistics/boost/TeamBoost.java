package fr.warzoumc.skyexpander.teamCore.core.team.file.core.statistics.boost;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public class TeamBoost {

    private Main main;
    private File statsFile;

    private String boostPath;
    private File boostFile;

    private JSONObject jsonObject;
    private JSONParser jsonParser = new JSONParser();

    private HashMap<String, Object> defaults = new HashMap<>();

    public TeamBoost(Main main, File statsFile) {
        this.main = main;
        this.statsFile = statsFile;

        this.boostPath = statsFile.getPath() + "/boost.json";
        this.boostFile = new File(boostPath);

        reload();
        //action
        save();
    }

    private void reload() {
        if (!boostFile.getParentFile().exists()){
            boostFile.getParentFile().mkdirs();
        }
        try {
            if (!boostFile.exists()){
                PrintWriter printWriter = new PrintWriter(boostFile, "UTF-8");
                printWriter.print("{");
                printWriter.print("}");
                printWriter.flush();
                printWriter.close();
                defaultsLevel();
            }
            this.jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(boostFile), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public int getGoldBoost(){
        return (int) getInteger("gold");
    }

    @SuppressWarnings("unchecked")
    private void defaultsLevel() {
        defaults.put("gold", 0);
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

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.boostFile), "UTF-8"));
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
