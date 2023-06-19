import org.json.*;

public class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }
    public static Item itemBuilderFromJSONObj(JSONObject obj) {
        return new Item(obj.get("name").toString());
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
