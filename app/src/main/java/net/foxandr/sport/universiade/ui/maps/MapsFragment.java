package net.foxandr.sport.universiade.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.news.NewsFragment;

import java.math.BigDecimal;

public class MapsFragment extends Fragment {

    private static final String ARG_PARAM1 = "longitude";
    private static final String ARG_PARAM2 = "latitude";
    private static final String ARG_PARAM3 = "venueName";

    private Double longitude;
    private Double latitude;
    private String venueName;

    private SupportMapFragment mapFragment;

    public static MapsFragment newInstance(Double longitude, Double latitude, String venueName) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, longitude);
        args.putDouble(ARG_PARAM2, latitude);
        args.putString(ARG_PARAM3, venueName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            longitude = getArguments().getDouble(ARG_PARAM1);
            latitude = getArguments().getDouble(ARG_PARAM2);
            venueName = getArguments().getString(ARG_PARAM3);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng urfu = new LatLng(longitude, latitude);
                googleMap.addMarker(new MarkerOptions().position(urfu).title(venueName));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(urfu, 14));
            }
        });
    }
}