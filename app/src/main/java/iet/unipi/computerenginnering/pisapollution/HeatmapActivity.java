package iet.unipi.computerenginnering.pisapollution;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HeatmapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HeatmapTileProvider mProvider;
    protected LatLng mCenterLocation = new LatLng(43.715531, 10.403133);
    private int idInquinante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                idInquinante = arg0.getSelectedItemPosition();
                initMapSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idInquinante=2;
                initMapSettings();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //initMapIfNecessary();
    }


    protected void initMapIfNecessary() {
        if (mMap != null) {
            return;
        }
        //mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback());
    }



    protected void initCamera() {
        CameraPosition position = CameraPosition.builder()
                .target( mCenterLocation )
                .zoom( getInitialMapZoomLevel() )
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }

    protected int getMapLayoutId() {
        return R.layout.activity_heatmap;
    }

    protected float getInitialMapZoomLevel() {
        return 12.0f;
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
        initMapSettings();
    }

    protected void initMapSettings() {

        double[] valori1=new double[7],valori2=new double[7],valori3=new double[7];
        ArrayList<LatLng> locations = new ArrayList<>();

        NetTask task = new NetTask();
        task.execute();
        try {
            String c =task.get();
            c=c.replace("{","");
            c=c.replace("}","");
            c=c.replace("[","");
            c=c.replace("]","");
            String[] tutto=c.split("\"");

            int j=0;
            for (int i=3;i<20;i+=8) {
                locations.add(j,new LatLng(Double.parseDouble(tutto[i]),Double.parseDouble(tutto[i+4])));
                j+=1;
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.get(1),12));

            j=0;
            for(int i=39;i<64;i+=4){
                valori1[j]=Double.parseDouble(tutto[i]);
                j+=1;
            }
            j=0;
            for(int i=79;i<104;i+=4){
                valori2[j]=Double.parseDouble(tutto[i]);
                j+=1;
            }
            j=0;
            for(int i=119;i<144;i+=4){
                valori3[j]=Double.parseDouble(tutto[i]);
                j+=1;
            }

            int x1=( (Double) Math.ceil( valori1[idInquinante]*100 ) ).intValue();
            int x2=( (Double) Math.ceil( valori2[idInquinante]*100 ) ).intValue();
            int x3=( (Double) Math.ceil( valori3[idInquinante]*100 ) ).intValue();
            for(int i=0;i<x1;i++) locations.add(locations.get(0));
            for(int i=0;i<x2;i++) locations.add(locations.get(1));
            for(int i=0;i<x3;i++) locations.add(locations.get(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mProvider = new HeatmapTileProvider.Builder().data( locations ).build();
        mProvider.setRadius(150);
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
}
