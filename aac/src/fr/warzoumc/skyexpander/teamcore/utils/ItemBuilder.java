package fr.warzoumc.skyexpander.teamcore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <i>Just open ItemBuilder</i>
 * @author WarzouMc
 */
public class ItemBuilder {

    /**
     * Vars
     */
    private Inventory inventory;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private SkullMeta skullMeta;

    private long position = -1;

    /**
     * init ItemBuilder without argument
     */
    public ItemBuilder(){
        this(Material.AIR);
    }

    /**
     * init ItemBuilder
     * @param material
     */
    public ItemBuilder(Material material){
        this(material, 1);
    }

    /**
     * init ItemBuilder
     * @param material
     * @param amount
     */
    public ItemBuilder(Material material, int amount) {
        this(material, amount, 0);
    }

    /**
     * init ItemBuilder
     * @param material
     * @param amount
     * @param data
     */
    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (byte)data);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * init ItemBuilder from his json object
     * @param jsonObject
     */
    public ItemBuilder(JSONObject jsonObject) {
        Material material = jsonObject.containsKey("m") ? Material.valueOf((String) jsonObject.get("m")) : Material.AIR;
        long amount = jsonObject.containsKey("a") ? (long) jsonObject.get("a") : 1;
        long data = jsonObject.containsKey("id") ? (long) jsonObject.get("id") : 0;

        this.itemStack = new ItemStack(material, (int) amount, (byte)data);
        this.itemMeta = itemStack.getItemMeta();

        long durability = jsonObject.containsKey("d") ? (long) jsonObject.get("d") : -1;

        setNewDurability((int)durability);

        if (jsonObject.containsKey("d_")){
            setName(c_S((String) jsonObject.get("d_")));
        }

        if (jsonObject.containsKey("l")){
            JSONArray jsonArray = (JSONArray) jsonObject.get("l");
            List<String> lore = jsonArray;
            List<String> setLore = new ArrayList<>();
            for (String string : lore) {
                setLore.add(c_S(string));
            }
            addLore(setLore);
        }

        if (jsonObject.containsKey("enchants")){
            JSONObject enchants = (JSONObject) jsonObject.get("enchants");
            JSONArray enchantsK = (JSONArray) enchants.get("k_");
            JSONArray enchantsV = (JSONArray) enchants.get("v_");
            Map<Enchantment, Integer> enchantment = new HashMap<>();
            for (int i = 0; i < enchantsK.size(); i++) {
                long v = (long) enchantsV.get(i);
                enchantment.put(Enchantment.getByName((String) enchantsK.get(i)), (int) v);
            }
            setEnchants(enchantment);
        }

        if (jsonObject.containsKey("i")){
            JSONArray jsonArray = (JSONArray) jsonObject.get("i");
            jsonArray.forEach(o -> addItemFlag(ItemFlag.valueOf((String) o)));
        }

        if (jsonObject.containsKey("p")){
            this.position = (long) jsonObject.get("p");
        }
    }

    /**
     * init ItemBuiler
     * @param itemStack
     */
    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Set item
     * @param material
     * @return
     */
    public ItemBuilder setItem(Material material){
        this.itemStack.setType(material);
        return this;
    }

    /**
     * Set amount
     * @param amount
     * @return
     */
    public ItemBuilder setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set data
     * @param data
     * @return
     */
    public ItemBuilder setData(int data){
        this.itemStack = new ItemStack(itemStack.getType(), itemStack.getAmount(), (byte)data);
        return this;
    }

    /**
     * Set ItemStack
     * @param itemStack
     * @return
     */
    public ItemBuilder setItemStack(ItemStack itemStack){
        this.itemStack = itemStack;
        return this;
    }

    /**
     * set this.inventory value
     * @param inventory
     * @return
     */
    public ItemBuilder inventory(Inventory inventory){
        this.inventory = inventory;
        return this;
    }

    /**
     * set the display name of the item
     * @param name
     * @return
     */
    public ItemBuilder setName(String name){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add lore from String list
     * @param lores
     * @return
     */
    public ItemBuilder addLore(List<String> lores){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add lore from String...
     * @param lores
     * @return
     */
    public ItemBuilder addLore(String... lores){
        addLore(Arrays.asList(lores));
        return this;
    }

    /**
     * add enchant to the item
     * @param enchantment
     * @param value
     * @param b
     * @return
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int value, boolean b){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, value, b);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * add enchants from map (use for json object)
     * @param enchantment
     * @return
     */
    public ItemBuilder setEnchants(Map<Enchantment, Integer> enchantment){
        for (Map.Entry<Enchantment, Integer> entry : enchantment.entrySet()) {
            Enchantment enchant = entry.getKey();
            addEnchantment(enchant, entry.getValue(), entry.getValue() > enchant.getMaxLevel());
        }
        return this;
    }

    /**
     * add ItemFlag on your item
     * @param itemFlag
     * @return
     */
    public ItemBuilder addItemFlag(ItemFlag itemFlag){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * remove ItemFlag on your item
     * @param itemFlag
     * @return
     */
    public ItemBuilder removeItemFlag(ItemFlag itemFlag){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * hide enchant
     * @return
     */
    public ItemBuilder hideEnchante(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * show enchant
     * @return
     */
    public ItemBuilder showEnchant(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Set durrability of item
     * /!\ 100 >= percent >= 0
     * @param percent
     * @return
     */
    public ItemBuilder setDurability(float percent){
        if (percent > 100.0){
            return this;
        }else if (percent < 0.0){
            return this;
        }
        itemStack.setDurability((short) (itemStack.getDurability() * (percent / 100)));
        return this;
    }

    /**
     * Set durrability of item
     * @param durability
     * @return
     */
    public ItemBuilder setNewDurability(int durability){
        itemStack.setDurability((short)durability);
        return this;
    }

    /**
     * If your item is a player skull you can apply a special player skull texture
     * @param playerName
     * @return
     */
    public ItemBuilder setSkullTextureFromePlayerName(String playerName){
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(playerName);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * If your item is a player skull you can apply a special player skull texture
     * @param player
     * @return
     */
    public ItemBuilder setSkullTexture(Player player){
        setSkullTextureFromePlayerName(player.getName());
        return this;
    }

    /**
     * If your item is a player skull you can apply a texture
     * value is the base64 value of the skull texture
     * You can find the value on https://minecraft-heads.com
     * @param value
     * @return
     */
    public ItemBuilder setSkullTexture(String value){
        this.skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value));

        try{
            Field gameProfileField = skullMeta.getClass().getDeclaredField("profile");
            gameProfileField.setAccessible(true);
            gameProfileField.set(skullMeta, gameProfile);
        } catch (IllegalAccessException | NoSuchFieldException error) {
            error.printStackTrace();
        }

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Inject item in inventory
     * @param inventory
     * @param position
     * @return
     */
    public ItemBuilder inject(Inventory inventory, int position){
        inventory.setItem(position, toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     * @param inventory
     * @return
     */
    public ItemBuilder inject(Inventory inventory){
        inventory.addItem(toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     * @param position
     * @return
     */
    public ItemBuilder inject(int position){
        inventory.setItem(position, toItemStack());
        return this;
    }

    /**
     * Inject item in inventory
     * @return
     */
    public ItemBuilder inject(){
        this.inventory.addItem(toItemStack());
        return this;
    }

    /**
     * Open inventory to the player
     * @param player
     */
    public void open(Player player){
        player.openInventory(inventory);
    }

    /**
     * get position
     * @return
     */
    public long getPosition(){
        return this.position;
    }

    /**
     * build item
     * @return
     */
    public ItemStack toItemStack(){
        return itemStack;
    }

    /**
     * get type
     * @return
     */
    public Material getMaterial(){
        return itemStack.getType();
    }

    /**
     * get amount
     * @return
     */
    public int getAmount(){
        return itemStack.getAmount();
    }

    /**
     * get data
     * @return
     */
    public int getData(){
        return itemStack.getData().getData();
    }

    /**
     * get durability
     * @return
     */
    public int getDurability(){
        return itemStack.getDurability();
    }

    /**
     * get item meta
     * @return
     */
    public ItemMeta getItemMeta(){
        return itemMeta;
    }

    /**
     * get display name
     * @return
     */
    public String getDisplayName(){
        return itemStack.hasItemMeta() && itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : null;
    }

    /**
     * get enchant
     * @return
     */
    public Map<Enchantment, Integer> getEnchantments(){
        return itemStack.hasItemMeta() && itemMeta.hasEnchants() ? itemMeta.getEnchants() : null;
    }

    /**
     * get lore
     * @return
     */
    public List<String> getLore(){
        return itemStack.hasItemMeta() && itemMeta.hasLore() ? itemMeta.getLore() : null;
    }

    /**
     * get item flag
     * @return
     */
    public Set<ItemFlag> getItemFlag(){
        return itemStack.hasItemMeta() && itemMeta.getItemFlags().size() > 0 ? itemMeta.getItemFlags() : null;
    }

    /**
     * parse in json object
     * @param savePosition
     * @return
     */
    @SuppressWarnings("uncheked")
    public JSONObject toJSONObject(int savePosition){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m", getMaterial());
        jsonObject.put("a", getAmount());
        jsonObject.put("id", getData());
        jsonObject.put("d", getDurability());
        if (getDisplayName() != null) jsonObject.put("d_", s_C(getDisplayName()));

        if (getEnchantments() != null){
            JSONObject enchants = new JSONObject();
            JSONArray enchantsK = new JSONArray();
            JSONArray enchantsV = new JSONArray();

            getEnchantments().forEach((enchantment, integer) -> {
                enchantsK.add(enchantment.getName());
                enchantsV.add(integer);
            });
            enchants.put("k_", enchantsK);
            enchants.put("v_", enchantsV);
            jsonObject.put("enchants", enchants);
        }

        if (getLore() != null) {
            JSONArray lores = new JSONArray();
            for (String s : getLore()) {
                lores.add(s_C(s));
            }

            jsonObject.put("l", lores);
        }

        if (getItemFlag() != null) {
            JSONArray itemFlag = new JSONArray();

            for (ItemFlag itemFlag1 : getItemFlag()) {
                itemFlag.add(itemFlag1.name());
            }

            jsonObject.put("i", itemFlag);
        }

        if (savePosition > -1) jsonObject.put("p", savePosition);
        return jsonObject;
    }

    /**
     * @param string
     * @return
     */
    private String s_C(String string){
        return string.replace("§", "&");
    }

    /**
     * @param string
     * @return
     */
    private String c_S(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}