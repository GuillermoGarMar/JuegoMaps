package com.example.guille.juegomaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 1;
    public static Circle circle, circle2, circle3;

    private static final String TAG = "gpslog";
    private LocationManager mLocMgr;
    private TextView lat, longt, objetv;
    public static double latidud, longitud;
    public static int distance, distance2, distance3, distance4, distance5;
    public static int radius, radius2, radius3, radius4;
    public static LatLng colegio, tesoro2, tesoro3, tesoro4, tesoroaux;
    public  static Marker marcador,marker,marker2,marker3,marker4;

    //definimos tiempo entre updates
    private static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = (long) 1; // 1 metro
    //Minimo tiempo para updates en Milisegundos
    private static final long MIN_TIEMPO_ENTRE_UPDATES = 250; // 1 sg

    Location miPosicion = new Location("mi posicion");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //enlazamos con el id del xml
        lat = (TextView) findViewById(R.id.latitud);
        longt = (TextView) findViewById(R.id.longitud);
        objetv = (TextView) findViewById(R.id.objetivo);


        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permisos para Android 6.0
            Log.e(TAG, "No se tienen permisos necesarios");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
            return;
        } else {
            Log.i(TAG, "Permisos necesarios.");
            //aqui configuramos la localizacion que se actualiza automaticamente en el medoto on changed
            //podemos usar un network provideer o un gps provider
            mLocMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, locListener, Looper.getMainLooper());
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        //asignamos los controles disponibles en el mapa
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        // Controles UI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        tesoroaux=new LatLng(42.236322, -8.714291);

        // Coordenadas y marcador entrada castelao
        colegio = new LatLng(42.237643, -8.714424);

        MarkerOptions markerOptions =
                new MarkerOptions()
                        .position(colegio)
                        .title("banana")//descripcion kiosco
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.Icono));//icono


        marker = googleMap.addMarker(markerOptions);

//tesoro 2
        tesoro2 = new LatLng(42.236877, -8.712793);

        MarkerOptions markerOptions2 =
                new MarkerOptions()
                        .position(tesoro2)
                        .title("banana")//descripcion pizza
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.Icono));//icono


        marker2 = googleMap.addMarker(markerOptions2);



        tesoro3=new LatLng(42.236760, -8.714729);
        MarkerOptions markerOptions3 =
                new MarkerOptions()
                        .position(tesoro3)
                        .title("banana")//descripcion callejon
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.Icono));//icono


        marker3 = googleMap.addMarker(markerOptions3);



        tesoro4=new LatLng(42.236779, -8.717204);
        MarkerOptions markerOptions4 =
                new MarkerOptions()
                        .position(tesoro4)
                        .title("banana")//descripcion fuente
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.Icono));//icono


        marker4 = googleMap.addMarker(markerOptions4);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }


    // recibe notificaciones del LocationManager
    public LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            latidud = location.getLatitude();
            longitud = location.getLongitude();

            lat.setText(String.valueOf(latidud));
            longt.setText((String.valueOf(longitud)));
            objetv.setText(String.valueOf(distance + ": m"));

            //enfoca mi posicion y añade mi icono
            LatLng coordenadas= new LatLng(latidud,longitud);
            CameraUpdate miUbi= CameraUpdateFactory.newLatLngZoom(coordenadas, 16);

            if(marcador!=null)marcador.remove();
            marcador=mMap.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title("Tu posicion actual")
                    
            );
            mMap.animateCamera(miUbi);


            //cambiamos icono myLocation por icono personalizado


            //calcula la distancia entre mi posicion y los tesoros
            seleccionaTesoro();

            //marca el tesoro anteriormente seleccionado

            calculadist(tesoroaux);


        }


        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled()");
        }

        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled()");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged()");
        }

        //calcula la distancia entre mi posicion y un tesoro ademas de manejar la visualizacion
        public void calculadist(LatLng treasure) {//recoge tesoro

            Location tesoro=new Location("tesoro");//recibo este tesoro de seleccionaTesoro()

            tesoro.setLatitude(treasure.latitude);
            tesoro.setLongitude(treasure.longitude);
//calculo mi distancia al tesoro seleccionado
            distance = (int) miPosicion.distanceTo(tesoro);

            //  Crear círculo con radio de 30m para cofre

            radius = 30;
            CircleOptions circleOptions = new CircleOptions()
                    .center(tesoroaux)
                    .radius(radius)
                    .strokeColor(Color.parseColor("#0D47A1"))
                    .strokeWidth(4)
                    .fillColor(Color.argb(32, 33, 150, 243));



            radius2 = 20;
            CircleOptions circleOptions2 = new CircleOptions()
                    .center(tesoroaux)
                    .radius(radius2)
                    .strokeColor(Color.parseColor("#FFED1212"))//rojo
                    .strokeWidth(4)
                    .fillColor(Color.argb(80, 255, 0, 0));


            radius3 = 10;
            CircleOptions circleOptions3 = new CircleOptions()
                    .center(tesoroaux)
                    .radius(radius3)
                    .strokeColor(Color.parseColor("#FF1AFF00"))//verde
                    .strokeWidth(4)
                    .fillColor(Color.argb(80, 0, 255, 0));



//añadimos la confi y creamos cadacirculo

//circulos invisibles
            circle=mMap.addCircle(circleOptions);
            circle.setVisible(false);
            circle2 = mMap.addCircle(circleOptions2);
            circle2.setVisible(false);
            circle3 = mMap.addCircle(circleOptions3);
            circle3.setVisible(false);


            if (distance <= radius && distance>radius2) {//<=30 & >20

                circle.setVisible(true);

            } else if (distance <= radius2 && distance>radius3) {//<20 &>10
                circle.setVisible(false);
                circle2.setVisible(true);

            } else if (distance <= radius3 && distance > radius4) {//<10 & >5
                circle2.setVisible(false);
                circle3.setVisible(true);

                //habilitamos qr


            }

        }


        //establece localizaciones  para compararlas
        public void seleccionaTesoro() {


            miPosicion.setLatitude(latidud);
            miPosicion.setLongitude(longitud);

            Location tes=new Location("tesoro1");

            Location tes2=new Location("tesoro2");

            Location tes3=new Location("tesoro3");
            Location tes4=new Location("tesoro4");

            tes.setLatitude(colegio.latitude);
            tes.setLongitude(colegio.longitude);

            tes2.setLatitude(tesoro2.latitude);
            tes2.setLongitude(tesoro2.longitude);

            tes3.setLatitude(tesoro3.latitude);
            tes3.setLongitude(tesoro3.longitude);

            tes4.setLatitude(tesoro4.latitude);
            tes4.setLongitude(tesoro4.longitude);

            distance2 = (int) miPosicion.distanceTo(tes); //calcula la distancia entre mi posicion y el tesoro

            distance3 = (int) miPosicion.distanceTo(tes2);

            distance4=(int)miPosicion.distanceTo(tes3);

            distance5=(int)miPosicion.distanceTo(tes4);

            if (distance2 <=60) {

                tesoroaux=colegio;

            } else if (distance3 <=60) {
                tesoroaux=tesoro2;


            } else if(distance4<=60){

                tesoroaux=tesoro3;

            } else if(distance5<=60){

                tesoroaux=tesoro4;
            }

        }

    };


}
