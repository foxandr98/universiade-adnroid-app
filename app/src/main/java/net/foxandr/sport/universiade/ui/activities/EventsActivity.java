package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.home.games.events.EventsDTO;
import net.foxandr.sport.universiade.ui.home.games.events.adapters.EventsDTOListAdapter;
import net.foxandr.sport.universiade.ui.home.games.mainsports.SportsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {

    SportsDTO sportDTO;
    Long gameId;
    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getSupportActionBar().setTitle(getResources().getString(R.string.schedule));
        Bundle arguments = getIntent().getExtras();
        locale = arguments.getString("locale");
        sportDTO = (SportsDTO)arguments.getSerializable("sportsDTO");
        gameId = arguments.getLong("gameId");
        setEvents(this);
    }

    private void setEvents(Context context) {
        ListView eventsDTOList = findViewById(R.id.events_dto_list);
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<EventsDTO>> call = api.getEventsByGameIdAndSportIdAndLocale(gameId, sportDTO.getSportId(), locale);

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