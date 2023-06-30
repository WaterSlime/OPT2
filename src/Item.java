import org.davidmoten.text.utils.WordWrap;
import org.json.*;

public class Item {
    private String name;
    private String description;

    public Item(String name, String desc) {
        this.name = name;
        this.description = desc;
    }
    public static Item itemBuilderFromJSONObj(JSONObject obj) {
        String desc = "";
        if (obj.getJSONArray("desc").length() != 0) {
            desc = obj.getJSONArray("desc").get(0).toString();
        }
        return new Item(obj.get("name").toString(), desc);
    }

    @Override
    public String toString() {
        String res = this.getName();
        if (!this.getDescription().equals("")) {
            res +=  "\n" + WordWrap.from(this.getDescription()).maxWidth(200).insertHyphens(true).wrap();
        }
        return res;
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
