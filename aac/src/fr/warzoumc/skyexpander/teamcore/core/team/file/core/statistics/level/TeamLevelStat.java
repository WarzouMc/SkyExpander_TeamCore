package fr.warzoumc.skyexpander.teamcore.core.team.file.core.statistics.level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.warzoumc.skyexpander.teamcore.main.Main;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public class TeamLevelStat {

    private Main main;
    private File statsFile;

    private String levelPath;
    private File levelFile;

    private JSONObject jsonObject;
    private JSONParser jsonParser = new JSONParser();

    private HashMap<String, Object> defaults = new HashMap<>();

    public TeamLevelStat(Main main, File statsFile) {
        this.main = main;
        this.statsFile = statsFile;

        this.levelPath = statsFile.getPath() + "/level.json";
        this.levelFile = new File(levelPath);

        reload();
        //action
        save();
    }

    public TeamLevelStat(Main main, File statsFile, boolean action) {
        this.main = main;
        this.statsFile = statsFile;

        this.levelPath = statsFile.getPath() + "/level.json";
        this.levelFile = new File(levelPath);

        reload();
    }

    private void reload() {
        if (!levelFile.getParentFile().exists()){
            levelFile.getParentFile().mkdirs();
        }
        try {
            if (!levelFile.exists()){
                PrintWriter printWriter = new PrintWriter(levelFile, "UTF-8");
                printWriter.print("{");
                printWriter.print("}");
                printWriter.flush();
                printWriter.close();
                defaultsLevel();
            }
            this.jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(levelFile), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public int getLevel(){
        return (int) getInteger("level");
    }

    public int getNeedMoney(){
        return (int) getInteger("needMoney");
    }

    public int getTotalMoney(){
        return (int) getInteger("totalMoney");
    }

    public void addLevel(int i){
        defaults.put("level", getLevel() + i);
    }

    public void setNeedMoney(int i){
        defaults.put("needMoney", i);
    }

    public void rvmTotalMoney(int i){
        defaults.put("totalMoney", getTotalMoney() - i);
    }

    public void addTotalMoney(int i){
        defaults.put("totalMoney", getTotalMoney() + i);
    }

    @SuppressWarnings("unchecked")
    private void defaultsLevel() {
        defaults.put("level", 1);
        defaults.put("needMoney", 1005);
        defaults.put("totalMoney", 0);
    }

    @SuppressWarnings("unchecked")
    public void save(){
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

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.levelFile), "UTF-8"));
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
