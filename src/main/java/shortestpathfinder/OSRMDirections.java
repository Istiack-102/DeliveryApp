package shortestpathfinder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class OSRMDirections {

    public static class RouteResult {
        public final double distanceMeters;
        public final double durationSeconds;
        public final String jsLatLngArray; // JavaScript array of [lat, lon] points for Leaflet

        public RouteResult(double distanceMeters, double durationSeconds, String jsLatLngArray) {
            this.distanceMeters = distanceMeters;
            this.durationSeconds = durationSeconds;
            this.jsLatLngArray = jsLatLngArray;
        }
    }

    // lat/lon in degrees
    public static RouteResult getRoute(double startLat, double startLon,
                                       double endLat, double endLon) {
        try {
            // OSRM expects lon,lat order
            String urlStr = String.format(Locale.US,
                    "https://router.project-osrm.org/route/v1/driving/%.6f,%.6f;%.6f,%.6f?overview=full&geometries=geojson",
                    startLon, startLat, endLon, endLat);

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "ShortestPathFinder/1.0 (your-email@example.com)");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject json = new JSONObject(sb.toString());
            JSONArray routes = json.getJSONArray("routes");
            if (routes.isEmpty()) {
                return null;
            }

            JSONObject route = routes.getJSONObject(0);
            double distance = route.getDouble("distance"); // meters
            double duration = route.getDouble("duration"); // seconds

            // geometry as GeoJSON
            JSONObject geometry = route.getJSONObject("geometry");
            JSONArray coords = geometry.getJSONArray("coordinates"); // [ [lon, lat], [lon, lat], ... ]

            // Build JS array: [ [lat, lon], [lat, lon], ... ]
            StringBuilder jsArray = new StringBuilder();
            jsArray.append("[");

            for (int i = 0; i < coords.length(); i++) {
                JSONArray pair = coords.getJSONArray(i);
                double lon = pair.getDouble(0);
                double lat = pair.getDouble(1);

                if (i > 0) jsArray.append(",");
                jsArray.append(String.format(Locale.US, "[%.6f, %.6f]", lat, lon));
            }

            jsArray.append("]");

            return new RouteResult(distance, duration, jsArray.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
