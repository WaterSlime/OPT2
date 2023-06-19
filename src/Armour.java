import org.json.*;

public class Armour extends Item {
    private String armourType;
    private String armourClass;

    public static void main(String[] args) {
        System.out.println(armourBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "scale-mail"))).toString());
        System.out.println(armourBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "chain-mail"))).toString());
    }
    public Armour(String name, String armourType, String armourClass) {
        super(name);
        this.armourType = armourType;
        this.armourClass = armourClass;
    }

    public static Armour armourBuilderFromJSONObj(JSONObject obj) {
        String name = obj.get("name").toString();
        String type = obj.get("armor_category").toString();
        JSONObject ACObj = obj.getJSONObject("armor_class");
        String AC = ACObj.get("base").toString();
        if (ACObj.get("dex_bonus").toString().equals("true")) {
            AC += " + DEX (max " + ACObj.get("max_bonus").toString() + ")";
        }
        return new Armour(name, type, AC);
    }

    @Override
    public String toString() {
        return "Armour{" +
                "name='" + this.getName() + '\'' +
                ", armourType='" + armourType + '\'' +
                ", armourClass='" + armourClass + '\'' +
                '}';
    }

    public String getArmourType() {
        return armourType;
    }

    public void setArmourType(String armourType) {
        this.armourType = armourType;
    }

    public String getArmourClass() {
        return armourClass;
    }

    public void setArmourClass(String armourClass) {
        this.armourClass = armourClass;
    }
}
