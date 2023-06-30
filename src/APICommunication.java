import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.json.*;

public class APICommunication {

    //Takes input for the type and the serach, and generates a url to use for an API request
    public static String APIRequestBuilder(String type, String search) {
        String apiURL = "https://www.dnd5eapi.co";
        if (type.equals("class") || type.equals("levels")) {
            apiURL += "/api/classes/" + search;
            if (type.equals("levels")) {
                apiURL += ("/levels");
            }
        } else if (type.equals("equipment")) {
            apiURL += "/api/equipment/" + search;
        } else if (type.equals("URL")) {
            apiURL += search;
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

    //Overloading of the APIRequest method to allow the same method to be used for JSONArrays instead of JSONObjects
    public static JSONArray APIRequest(String APIURL, boolean array) {
        String jsonString = "";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(APIURL)).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonString = response.body();
        } catch (Exception e) {
            System.out.println("Something went wrong with the HTTP request");
        }
        return new JSONArray(jsonString);
    }
}
