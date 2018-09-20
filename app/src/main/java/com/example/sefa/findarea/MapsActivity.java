package com.example.sefa.findarea;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.SphericalUtil;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.IOException;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    final PolygonOptions polygonOptions = new PolygonOptions();

    Polyline polyline2;
    final  PolygonOptions polygonOptions1=new PolygonOptions();
    private static final int PERMISSION_REQUEST_CODE = 1;

    Button button1;
    ArrayList<LatLng> points = new ArrayList<LatLng>();
    ArrayList<LatLng> pointsDraw = new ArrayList<LatLng>();
    private static final String TAG = "taagggg";
    private GoogleMap mMap;
    List<LatLng> latLngs = new ArrayList<>();
    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Button button = (Button) findViewById(R.id.button1);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling

            return;
        }



        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            //get the latitude
                            double latitude = location.getLatitude();
                            //get the longitude
                            double longitude = location.getLongitude();
                            //instantiate the class, LatLng
                            LatLng latLng = new LatLng(latitude, longitude);
                            //instantiate the class, Geocoder
                            Geocoder geocoder = new Geocoder(getApplicationContext());

                            try {


                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                polyline2 = null;
                                String str = addressList.get(0).getLatitude() + ",";
                                str += addressList.get(0).getLongitude();


                                latLngs.add(latLng);

                                points.add(latLng);
                                polygonOptions.add(latLng);


                                mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                                mMap.addPolyline(new PolylineOptions().addAll(points));
                                mMap.addPolygon(new PolygonOptions()
                                        .addAll(points)
                                        .strokeWidth(0x5500ff00)
                                        .fillColor(0x5500ff00)
                                        .strokeColor(0x5500ff00));




                                Log.i(TAG, "computeArea " + SphericalUtil.computeArea(latLngs));
                                String string = Double.toString(SphericalUtil.computeArea(latLngs));
                                TextView textView = findViewById(R.id.textview);
                                textView.setText(string);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }


                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    }


            );


        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Polygon polygon = null;

                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the longitude
                    double longitude = location.getLongitude();
                    //instantiate the class, LatLng
                    LatLng latLng = new LatLng(latitude, longitude);
                    //instantiate the class, Geocoder
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {


                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        polyline2 = null;

                        String str = addressList.get(0).getLatitude() + ",";
                        str += addressList.get(0).getLongitude();



                        latLngs.add(latLng);

                        points.add(latLng);
                        polygonOptions.add(latLng);


                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        mMap.addPolyline(new PolylineOptions().addAll(points));
                        mMap.addPolygon(new PolygonOptions()
                                .addAll(points)
                                .strokeWidth(0x5500ff00)
                                .fillColor(0x5500ff00)
                                .strokeColor(0x5500ff00));




                        Log.i(TAG, "computeArea " + SphericalUtil.computeArea(latLngs));
                        String string = Double.toString(SphericalUtil.computeArea(latLngs));
                        TextView textView = findViewById(R.id.textview);
                        textView.setText(string+ " m²");


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }



        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMap();
            }
        });
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { drawMap();   }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling


            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);

    }


    public void clearMap() {

        mMap.clear();
        latLngs.clear();
        points.clear();
        polygonOptions1.getPoints().clear();
        polygonOptions.getPoints().clear();
        pointsDraw.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings();
        mMap.setOnMapClickListener(null);

    }

    public void drawMap(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling



        }
        pointsDraw.clear();
        mMap.setMyLocationEnabled(false);
        mMap.clear();

        //polygonOptions1.getPoints().clear();



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                MarkerOptions markerOptions1 = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude));
                mMap.addMarker(markerOptions1);
                pointsDraw.add(markerOptions1.getPosition());
               // polygonOptions1.add(markerOptions1.getPosition());

                mMap.addPolygon(new PolygonOptions().addAll(pointsDraw)
                        .fillColor(0x5500ff00)
                        .strokeColor(0x5500ff00)
                        .strokeWidth(4.5f)
                );

                String string = Double.toString(SphericalUtil.computeArea(pointsDraw));
                TextView textView = findViewById(R.id.textview);
                textView.setText(string+" m²");

            }

        });
    }


    @Override
    public void onClick(View view) {

    }
}
