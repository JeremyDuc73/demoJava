import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChuckNorrisJokes {
    public String getJoke() {
        String joke = "";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                joke = jsonResponse.getString("value");
            } else {
                System.out.println("Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return joke;
    }
}
