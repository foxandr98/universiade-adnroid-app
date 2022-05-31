package net.foxandr.sport.universiade.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.foxandr.sport.universiade.R;

public class VenueDetailsActivity extends AppCompatActivity {

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);

        Fragment fragment = new MapsFragment();

        // Получить фрагмент карты и быть уведомленным, когда карта готова к использованию
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.venue_frame_layout, fragment)
                .commit();
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }


}