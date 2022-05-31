package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.jakewharton.threetenabp.AndroidThreeTen;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.commondto.VenuesEntity;
import net.foxandr.sport.universiade.ui.maps.MapsFragment;

public class VenueDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        getSupportActionBar().setTitle(getResources().getString(R.string.venues_details_title));
        VenuesEntity venuesEntity = (VenuesEntity) getIntent().getExtras().getSerializable("venuesEntity");

        TextView venueNameView = findViewById(R.id.venue_details_stadium_name);
        TextView venueAddressView = findViewById(R.id.venue_details_address);

        VenuesEntity.VenuesTEntity venueTranslatedInfo = venuesEntity.getVenuesTEntities().get(0);
        venueNameView.setText(venueTranslatedInfo.getVenueName());
        venueAddressView.setText(venueTranslatedInfo.getAddress());

        Fragment fragment = MapsFragment.newInstance(
                venuesEntity.getLongitude(),
                venuesEntity.getLatitude(),
                venueTranslatedInfo.getVenueName());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.venue_fragment_container, fragment, null)
                .commit();
        AndroidThreeTen.init(this);
    }
}