package fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.permission;

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

public class TeamGroupPermission {

    private Main main;
    private File groupsFile;
    private String groupName;

    private String permPath;
    private File permFile;

    private JSONObject jsonObject;
    private JSONParser jsonParser = new JSONParser();

    private HashMap<String, Object> defaults = new HashMap<>();

    public TeamGroupPermission(Main main, File groupsFile, String groupName) {
        this.main = main;
        this.groupsFile = groupsFile;
        this.groupName = groupName;

        this.permPath = groupsFile.getPath() + "/permission.json";
        this.permFile = new File(permPath);

        reload();
        //action
        save();
    }

    private void reload() {
        if (!permFile.getParentFile().exists()){
            permFile.getParentFile().mkdirs();
        }
        try {
            if (!permFile.exists()){
                PrintWriter printWriter = new PrintWriter(permFile, "UTF-8");
                printWriter.print("{");
                printWriter.print("}");
                printWriter.flush();
                printWriter.close();
                defaultsPerm();
            }
            this.jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(permFile), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void defaultsPerm() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("skyexpander.team." + groupName);
        defaults.put("perms", jsonArray);
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

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.permFile), "UTF-8"));
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
