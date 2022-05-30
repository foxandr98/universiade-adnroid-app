package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.home.games.events.EventsDTO;
import net.foxandr.sport.universiade.ui.home.games.events.adapters.EventsDTOListAdapter;
import net.foxandr.sport.universiade.ui.home.games.sports.SportsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {

    SportsDTO sportsDTO;
    Long gameId;
    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getSupportActionBar().setTitle(getResources().getString(R.string.schedule));
        Bundle arguments = getIntent().getExtras();
        locale = arguments.getString("locale");
        sportsDTO = (SportsDTO) arguments.getSerializable("sportsDTO");
        gameId = arguments.getLong("gameId");
        setHeader(this);
        setEvents(this);
    }

    private void setHeader(Context context) {
        ImageView imageView = findViewById(R.id.events_sport_image);
        TextView textView = findViewById(R.id.events_sport_name);
        textView.setText(sportsDTO.getSportName());
        imageView.setImageResource(getResources().
                getIdentifier("sport_" + sportsDTO.getSportCode().toLowerCase(),
                        "drawable",
                        context.getPackageName()));
    }

    private void setEvents(Context context) {
        ListView eventsDTOList = findViewById(R.id.events_dto_list);
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<EventsDTO>> call = api.getEventsByGameIdAndSportIdAndLocale(gameId, sportsDTO.getSportId(), locale);

        call.enqueue(new Callback<List<EventsDTO>>() {
            @Override
            public void onResponse(Call<List<EventsDTO>> call, Response<List<EventsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<EventsDTO> resource = response.body();
                EventsDTOListAdapter eventsDTOListAdapter = new EventsDTOListAdapter(
                        context,
                        R.layout.events_list_item,
                        resource);

                eventsDTOList.setAdapter(eventsDTOListAdapter);

                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        EventsDTO selectedEventsDTO = (EventsDTO) parent.getItemAtPosition(position);
                        Toast.makeText(context,
                                getResources().getString(R.string.you_chose) + selectedEventsDTO.getId(),
                                Toast.LENGTH_SHORT).show();
                    }
                };
                eventsDTOList.setOnItemClickListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<EventsDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Events query network error");
                call.cancel();
            }
        });

    }


}