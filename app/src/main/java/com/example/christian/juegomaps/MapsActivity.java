package com.example.christian.juegomaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "gpslog";
    private static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = (long) 1; // 20 metro
    private static final long MIN_TIEMPO_ENTRE_UPDATES = 100; // 1 sg
    private final static int codigo = 0;
    public Intent intento;
    private LocationManager mLocMgr;
    private TextView textViewGPS, textViewDist;
    private LatLng tesoro;
    private Location tesoroLoc;
    private double lat = 42.236862, lng = -8.714710;
    private TextView reciboDato;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        textViewGPS = (TextView) findViewById(R.id.lat);
        textViewDist = (TextView) findViewById(R.id.dist);

        reciboDato = (TextView) findViewById(R.id.recibo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        String salutation = "Adios";

        intento = new Intent(MapsActivity.this, SimpleScannerActivity.class);
        intento.putExtra("salutation", salutation);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivityForResult(intento, codigo);
            }
        });
        /////////////////////

        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "No se tienen permisos necesarios!, se requieren.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
            return;
        } else {
            Log.i(TAG, "Permisos necesarios OK!.");
            mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, locListener, Looper.getMainLooper());
        }
        textViewGPS.setText("Lat " + " Long ");
        tesoro = new LatLng(lat, lng);
        tesoroLoc = new Location(LocationManager.GPS_PROVIDER);
        tesoroLoc.setLatitude(lat);
        tesoroLoc.setLongitude(lng);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        //googleMap.addMarker(new MarkerOptions()
        //        .position(tesoro))
        //.title("Marca de Tesoro")
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(tesoro));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(tesoro));
    }

    public LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.i(TAG, "Lat " + location.getLatitude() + " Long " + location.getLongitude());
            textViewGPS.setText("Lat " + (float) location.getLatitude() + " Long " + (float) location.getLongitude());

            // movemos la camara para la nueva posicion
            LatLng nuevaPosicion = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(nuevaPosicion)
                    .zoom(15)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //calculamos la distancia a la marca

            String dista = String.valueOf((int) location.distanceTo(tesoroLoc));
            int distancia = (int) location.distanceTo(tesoroLoc);

            if(distancia < 3){
                textViewDist.setText("X");
                textViewDist.setTextSize(50);
                textViewDist.setTextColor(Color.RED);
            }else{
                textViewDist.setText(dista + " metros");
                textViewDist.setTextSize(25);
                textViewDist.setTextColor(Color.BLACK);
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onProviderDisabled()");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled()");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onStatusChanged()");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == codigo) {
                if(data.toString().equals("Tesoro")){
                    reciboDato.setText("Has encontrado el tesoro");
                }else{
                    reciboDato.setText("Eso no es un tesoro");
                }
            }
        }
    }
}
