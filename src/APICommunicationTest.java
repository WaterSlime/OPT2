import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

class APICommunicationTest {
    String test1 = APICommunication.APIRequestBuilder("class", "fighter");
    String test2 = APICommunication.APIRequestBuilder("levels", "druid");
    String test3 = APICommunication.APIRequestBuilder("equipment", "longsword");
    String test4 = APICommunication.APIRequestBuilder("equipment", "abacus");
    JSONObject obj1 = APICommunication.APIRequest(test1);
    JSONArray obj2 = APICommunication.APIRequest(test2, true);
    JSONObject obj3 = APICommunication.APIRequest(test3);
    JSONObject obj4 = APICommunication.APIRequest(test4);
    @org.junit.jupiter.api.Test
    void APIRequestBuilder() {
        assertEquals(test1, "https://www.dnd5eapi.co/api/classes/fighter");
        assertEquals(test2, "https://www.dnd5eapi.co/api/classes/druid/levels");
        assertEquals(test3, "https://www.dnd5eapi.co/api/equipment/longsword");
        assertEquals(test4, "https://www.dnd5eapi.co/api/equipment/abacus");
        assertNotEquals(test2, "https://www.dnd5eapi.co/api/classes/druid");
    }

    @org.junit.jupiter.api.Test
    void APIRequest() {
        assertEquals(obj1.get("name").toString(), "Fighter");
        assertEquals(obj3.getJSONObject("equipment_category").get("name").toString(), "Weapon");
        assertEquals(obj4.get("weight").toString(), "2");
    }

    @org.junit.jupiter.api.Test
    void testAPIRequest() {
        assertEquals(obj2.getJSONObject(0).get("level").toString(), "1");
        assertEquals(obj2.getJSONObject(1).getJSONObject("spellcasting").get("cantrips_known").toString(), "2");
    }
}