package fr.warzoumc.skyexpander.teamCore.core.team.file.core.groups.propertys;

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

public class TeamGroupProperty {

    private Main main;
    private File groupsFile;
    private String groupName;

    private String propertyPath;
    private File propertyFile;

    private JSONObject jsonObject;
    private JSONParser jsonParser = new JSONParser();

    private HashMap<String, Object> defaults = new HashMap<>();

    public TeamGroupProperty(Main main, File groupsFile, String groupName, int action) {
        this.main = main;
        this.groupsFile = groupsFile;
        this.groupName = groupName;

        this.propertyPath = groupsFile.getPath() + "/property.json";
        this.propertyFile = new File(propertyPath);

        reload();
        if (action != -1){
            save();
        }
    }

    public TeamGroupProperty(Main main, File groupsFile, String groupName) {
        this.main = main;
        this.groupsFile = groupsFile;
        this.groupName = groupName;

        this.propertyPath = groupsFile.getPath() + "/property.json";
        this.propertyFile = new File(propertyPath);

        reload();
        //action
        save();
    }

    private void reload() {
        if (!propertyFile.getParentFile().exists()){
            propertyFile.getParentFile().mkdirs();
        }
        try {
            if (!propertyFile.exists()){
                PrintWriter printWriter = new PrintWriter(propertyFile, "UTF-8");
                printWriter.print("{");
                printWriter.print("}");
                printWriter.flush();
                printWriter.close();
                defaultsProperty();
            }
            this.jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(propertyFile), "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String getGroupDef(){
        return (String) getObject("chatSetting").get("def");
    }

    @SuppressWarnings("unchecked")
    private void defaultsProperty() {
        JSONObject chatSettings = new JSONObject();
        chatSettings.put("format", "[teamchat] [def][player] §r§b>");
        if (groupName.equalsIgnoreCase("owner")){
            chatSettings.put("def", "§f[§cChef de team§f]");
        } else if (groupName.equalsIgnoreCase("general")){
            chatSettings.put("def", "§f[§eGeneral de team§f]");
        } else if (groupName.equalsIgnoreCase("sergent")){
            chatSettings.put("def", "§f[§bSergent de team§f]");
        } else if (groupName.equalsIgnoreCase("player")){
            chatSettings.put("def", "§f[§7Joueur de team§f]");
        }

        defaults.put("chatSetting", chatSettings);
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

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.propertyFile), "UTF-8"));
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
