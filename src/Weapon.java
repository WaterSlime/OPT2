import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;

public class Weapon extends Item {
    private String weaponType;
    private String weaponDamage;
    private String damageType;
    private ArrayList<String> weaponProperties = new ArrayList<String>();

    public static void main(String[] args) {
        System.out.println(weaponBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "longsword"))).toString());
    }

    public Weapon(String name, String weaponType, String weaponDamage, String damageType, ArrayList<String> weaponProperties) {
        super(name);
        this.weaponType = weaponType;
        this.weaponDamage = weaponDamage;
        this.damageType = damageType;
        this.weaponProperties = weaponProperties;
    }

    public static Weapon weaponBuilderFromJSONObj(JSONObject obj) {
        String name = obj.get("name").toString();
        String type = obj.get("category_range").toString();
        String damage = obj.getJSONObject("damage").get("damage_dice").toString();
        if (obj.has("two_handed_damage")) {
            damage += " / " + obj.getJSONObject("two_handed_damage").get("damage_dice").toString();
        }
        String damageType = obj.getJSONObject("damage").getJSONObject("damage_type").get("name").toString();
        ArrayList<String> properties = new ArrayList<String>();
        Iterator itr = obj.getJSONArray("properties").iterator();
        while(itr.hasNext()) {
            Object nextProp = itr.next();
            if (nextProp instanceof JSONObject) {
                JSONObject nextJSON = (JSONObject) nextProp;
                properties.add(nextJSON.get("name").toString());
            }
        }
        return new Weapon(name, type, damage, damageType, properties);
    }

    @Override
    public String toString() {
        return String.format("%-20s%-20s%-20s%-20s\n%s", this.getName(), this.getWeaponType(), this.getWeaponDamage(), this.getDamageType(), this.getWeaponProperties());
    }


    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public String getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(String weaponDamage) {
        this.weaponDamage = weaponDamage;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public ArrayList<String> getWeaponProperties() {
        return weaponProperties;
    }

    public void setWeaponProperties(ArrayList<String> weaponProperties) {
        this.weaponProperties = weaponProperties;
    }
}
