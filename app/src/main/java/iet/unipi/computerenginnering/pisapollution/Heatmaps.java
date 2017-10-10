/*package iet.unipi.computerenginnering.pisapollution;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Heatmaps extends AppCompatActivity {

    private SupportMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmaps);
        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);//remember getMap() is deprecated!
    }

    //@Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(47.17, 27.5699), 16));
        map.addMarker(new MarkerOptions()               .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(47.17, 27.5699))); //Iasi, Romania
        map.setMyLocationEnabled(true);
    }
}
*/