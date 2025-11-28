package shortestpathfinder;

import javafx.scene.web.WebView;

public class OSMMapView {

    public static void loadRoute(WebView webView, String jsLatLngArray) {

        webView.getEngine().setUserAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/120.0 Safari/537.36"
        );

        String html = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <meta charset="utf-8">

                  <style>
                    html, body {
                        height: 100%%;
                        width: 100%%;
                        margin: 0;
                        padding: 0;
                        overflow: hidden;
                    }

                    #map {
                        height: 100%%;
                        width: 100%%;
                        min-height: 550px;
                    }

                    .leaflet-container {
                        height: 100%% !important;
                        width: 100%% !important;
                    }
                  </style>

                  <link rel="stylesheet"
                        href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
                </head>

                <body>
                  <div id="map"></div>

                  <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

                  <script>

                    document.addEventListener("DOMContentLoaded", function() {

                      var map = L.map('map', { zoomControl: true }).setView([23.8103, 90.4125], 13);

                      // ✔ Street Map (default)
                      var streetLayer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        maxZoom: 19,
                        attribution: '&copy; OpenStreetMap contributors'
                      });

                      // ✔ Satellite Layer (FREE from ESRI)
                      var satelliteLayer = L.tileLayer(
                        'https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
                          maxZoom: 19,
                          attribution: 'Tiles &copy; Esri &mdash; Source: Esri, Maxar, Earthstar Geographics'
                        });

                      // add default layer
                      streetLayer.addTo(map);

                      // ✔ Add layer switcher
                      L.control.layers(
                        { "Street Map": streetLayer, "Satellite View": satelliteLayer },
                        {},
                        { collapsed: false }
                      ).addTo(map);


                      // Load route data
                      var routeCoords = %s;

                      if (routeCoords.length > 0) {
                        var routeLine = L.polyline(routeCoords, { color: 'red', weight: 5 }).addTo(map);
                        map.fitBounds(routeLine.getBounds());

                        L.marker(routeCoords[0]).addTo(map).bindPopup('Start').openPopup();
                        L.marker(routeCoords[routeCoords.length - 1]).addTo(map).bindPopup('End');
                      }

                      setTimeout(() => map.invalidateSize(true), 500);
                    });

                  </script>
                </body>
                </html>
                """, jsLatLngArray);

        webView.getEngine().setJavaScriptEnabled(true);
        webView.getEngine().loadContent(html);
    }
}
