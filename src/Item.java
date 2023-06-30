import org.json.*;

public class Item {
    private String name;
    private String description;

    public Item(String name, String desc) {
        this.name = name;
        this.description = desc;
    }
    public static Item itemBuilderFromJSONObj(JSONObject obj) {
        String desc = obj.getJSONArray("desc").get(0).toString();
        return new Item(obj.get("name").toString(), desc);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
