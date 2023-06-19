import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.json.*;

public class APICommunication {

    public static void main(String[] args) {

//        String jsonRequestResult = APIRequest(APIRequestBuilder("class", "cleric"));
//        JSONObject obj = new JSONObject(jsonRequestResult);

    }

    //Takes input for the type, turns it into the correct API url to use for an API request
    public static String APIRequestBuilder(String type, String search) {
        String apiURL = "https://www.dnd5eapi.co/api/";
        if (type.equals("class") || type.equals("levels")) {
            apiURL += "classes";
        } else if (type.equals("equipment")) {
            apiURL += "equipment/" + search;
        }
        if (type.equals("class")) {
            apiURL += "/" + search;
        }

        return apiURL;
    }

    //Takes an API URL as input and attempts to return a string containing the JSON required to build an object
    public static JSONObject APIRequest(String APIURL) {
        String jsonString = "";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(APIURL)).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonString = response.body();
        } catch (Exception e) {
            System.out.println("Something went wrong with the HTTP request");
        }
        return new JSONObject(jsonString);
    }
}
