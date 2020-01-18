package fr.warzoumc.skyexpander.teamcore.core.team.file.core.groups;

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
import java.util.List;
import java.util.TreeMap;

public class TeamGroupList {

    private Main main;
    private File groupsFile;

    private String listPath;
    private File listFile;

    private JSONObject jsonObject;
    private JSONParser jsonParser = new JSONParser();

    private HashMap<String, Object> defaults = new HashMap<>();

    public TeamGroupList(Main main, File groupsFile) {
        this.main = main;
        this.groupsFile = groupsFile;

        this.listPath = groupsFile.getPath() + "/list.json";
        this.listFile = new File(listPath);

        reload();
        //action
        save();
    }

    private void reload() {
        if (!listFile.getParentFile().exists()){
            listFile.getParentFile().mkdirs();
        }
        try {
            if (!listFile.exists()){
                PrintWriter printWriter = new PrintWriter(listFile, "UTF-8");
                printWriter.print("{");
                printWriter.print("}");
                printWriter.flush();
                printWriter.close();
                defaultsGroups();
            }
            this.jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(listFile), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getGroups(){
        return (List<String>) getArray("groups");
    }

    @SuppressWarnings("unchecked")
    private void defaultsGroups() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("owner");
        jsonArray.add("general");
        jsonArray.add("sergent");
        jsonArray.add("player");
        defaults.put("groups", jsonArray);
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

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.listFile), "UTF-8"));
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
