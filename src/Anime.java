import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class Anime {

    private String name;
    private ArrayList<String> genres;
    private String studio;
    private int numberOfEpisodes;

    public Anime(String name, int numberOfEpisodes, String studio, ArrayList<String> genres) {
        this.name = name;
        this.numberOfEpisodes = numberOfEpisodes;
        this.studio = studio;
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "<html><body>" +
                "Name: " + name + "<br>" +
                "Episodes: " + numberOfEpisodes + "<br>" +
                "Studio: " + studio + "<br>" +
                "Genres: " + String.join(", ", genres) +
                "</body></html>";
    }

    public static List<Anime> getAnimes() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sc)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://127.0.0.1:8000/api/animes"))
                    .GET()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE3MzAyMTI3MjcsImV4cCI6MTczMDIxNjMyNywicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJ1c2VybmFtZSI6ImplcmVteSJ9.B0sBuZamO3b0fPf9NVsOoNf3Se1ZLCBD2rIbPRHxgGIuLSXlQQqhHmwDhUHoONx9oaVlfx27ci-XfK6GspIrKRNtJBCGa8oFTXyUiRbaPLfTCrQ2q2SGEY8uda9LlkAqjQ4uyILwLrZKy6f3FVutLJOxR27dit99E9gXdJcWZRqfdMbQT2f7uy44wGkImBEIlXL9KOaxfkG7r0GbLP4HWNWhLZgK5FpMI450cp6BlIUMxujxomPiYl1L1tA1XfbpnnD_AE3b7GTsb86_O6DQrKgELaVKSb975e8WPk2qJnZjY73K2Ux0GUFb1vVg2qx0PmWjkPhe3kw7_XzInaQrdQ")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONArray jsonResponse = new JSONArray(response.body());
                List<Anime> animes = new ArrayList<>();
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject animeObject = jsonResponse.getJSONObject(i);
                    String name = animeObject.getString("name");
                    int numberOfEpisodes = animeObject.getInt("numberOfEpisodes");
                    JSONArray genresObjects = animeObject.getJSONArray("genres");
                    ArrayList<String> genres = new ArrayList<>();
                    for (int j = 0; j < genresObjects.length(); j++) {
                        JSONObject genreObject = genresObjects.getJSONObject(j);
                        genres.add(genreObject.getString("name"));
                    }
                    JSONObject studioObject = animeObject.getJSONObject("studio");
                    String studio = studioObject.getString("name");

                    Anime anime = new Anime(name, numberOfEpisodes, studio, genres);
                    animes.add(anime);

                }
                return animes;
            } else {
                System.out.println("Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

