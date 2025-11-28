package shortestpathfinder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OSMGeocoder {

    // Photon API (OpenStreetMap geocoding)
    private static final String PHOTON_URL = "https://photon.komoot.io/api/?limit=1&q=";

    public static double[] geocode(String query) {
        try {
            // Always append Dhaka + Bangladesh for accuracy
            String fullQuery = query + ", Dhaka, Bangladesh";

            String encoded = URLEncoder.encode(fullQuery, StandardCharsets.UTF_8);
            URL url = new URL(PHOTON_URL + encoded);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Photon does NOT require user-agent
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject json = new JSONObject(sb.toString());
            JSONArray features = json.getJSONArray("features");

            if (features.length() == 0) {
                System.out.println("Photon could not find: " + fullQuery);
                return null;
            }

            JSONObject geometry = features.getJSONObject(0).getJSONObject("geometry");
            JSONArray coords = geometry.getJSONArray("coordinates");

            double lon = coords.getDouble(0);
            double lat = coords.getDouble(1);

            System.out.println("Geocoded: " + fullQuery + " -> " + lat + ", " + lon);

            return new double[]{lat, lon};

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
