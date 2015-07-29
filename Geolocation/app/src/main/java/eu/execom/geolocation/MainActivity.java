package eu.execom.geolocation;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.RoadsApi;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String API_KEY = "AIzaSyD6gZ5-8DdmbhgLmU153AOJc_n8K-lQAPw";
    private static final String EXECOM_LOCATION = "45.2561566,19.7951974";
    private static final int PAGE_SIZE_LIMIT = 100;
    private static final int PAGINATION_OVERLAP = 5;

    private GeoApiContext geoApiContext;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geoApiContext = new GeoApiContext().setApiKey(API_KEY);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        final Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        if (lastLocation != null) {
                            map.addMarker(new MarkerOptions()
                                    .position(new com.google.android.gms.maps.model.LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new com.google.android.gms.maps.model.LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 12), 2000, null);

                            try {
                                final ArrayList<LatLng> directions = getDirections(lastLocation.getLatitude() + "," + lastLocation.getLongitude(), EXECOM_LOCATION);
                                final List<SnappedPoint> snappedPoints = snapToRoads(geoApiContext, directions);
                                drawRouteOnMap(snappedPoints);
                            } catch (Exception e){
                                Log.e(TAG, "Error getting directions.", e);
                            }
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d(TAG, "Google API connection suspended.");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.e(TAG, "Google API connection failed: " + connectionResult.getErrorCode());
                    }
                })
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private ArrayList<LatLng> getDirections(String origin, String destination) throws Exception {
        final ArrayList<LatLng> directions = new ArrayList<>();
        final DirectionsRoute[] routes = DirectionsApi.getDirections(geoApiContext, origin, destination).await();
        for (DirectionsRoute route: routes){
            for (DirectionsLeg leg: route.legs){
                for (DirectionsStep step: leg.steps) {
                    final List<LatLng> latLngs = step.polyline.decodePath();
                    directions.addAll(latLngs);
                }
            }
        }
        return directions;
    }

    private List<SnappedPoint> snapToRoads(GeoApiContext context, ArrayList<LatLng> wayPoints) throws Exception {
        final List<SnappedPoint> snappedPoints = new ArrayList<>();

        int offset = 0;
        while (offset < wayPoints.size()) {
            if (offset > 0) {
                offset -= PAGINATION_OVERLAP;
            }

            final int lowerBound = offset;
            final int upperBound = Math.min(offset + PAGE_SIZE_LIMIT, wayPoints.size());

            final LatLng[] page = wayPoints
                    .subList(lowerBound, upperBound)
                    .toArray(new LatLng[upperBound - lowerBound]);

            final SnappedPoint[] points = RoadsApi.snapToRoads(context, true, page).await();
            boolean passedOverlap = false;
            for (SnappedPoint point : points) {
                if (offset == 0 || point.originalIndex >= PAGINATION_OVERLAP - 1) {
                    passedOverlap = true;
                }
                if (passedOverlap) {
                    snappedPoints.add(point);
                }
            }
            offset = upperBound;
        }

        return snappedPoints;
    }

    private void drawRouteOnMap(List<SnappedPoint> snappedPoints) {
        final com.google.android.gms.maps.model.LatLng[] mapPoints = new com.google.android.gms.maps.model.LatLng[snappedPoints.size()];
        for (int i = 0; i < snappedPoints.size(); i++) {
            final SnappedPoint snappedPoint = snappedPoints.get(i);
            mapPoints[i] = new com.google.android.gms.maps.model.LatLng(snappedPoint.location.lat, snappedPoint.location.lng);
        }
        map.addPolyline(new PolylineOptions().add(mapPoints).color(Color.BLUE));
    }
}
