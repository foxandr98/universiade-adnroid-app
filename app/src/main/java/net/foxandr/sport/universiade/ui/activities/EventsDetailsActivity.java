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
import net.foxandr.sport.universiade.ui.home.games.events.adapters.EventsDetailsListAdapter;
import net.foxandr.sport.universiade.ui.home.games.events.competitors.CompetitorsResultsDTO;
import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.GenderDisciplinesEntity;
import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.disciplines.DisciplinesEntity;
import net.foxandr.sport.universiade.ui.home.games.events.stages.StagesEntity;
import net.foxandr.sport.universiade.ui.home.games.sports.SportsDTO;
import net.foxandr.sport.universiade.utils.DataBaseEnumsUtils;
import net.foxandr.sport.universiade.utils.TimeParser;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsDetailsActivity extends AppCompatActivity {

    EventsDTO eventsDTO;
    String locale;
    Long gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);
        getSupportActionBar().setTitle(getResources().getString(R.string.events_details_results));

        Bundle arguments = getIntent().getExtras();
        locale = arguments.getString("locale");
        gameId = arguments.getLong("gameId");
        eventsDTO = (EventsDTO) arguments.getSerializable("eventsDTO");

        setHeader(this);
        setCompetitorsResults(this);
    }


    private void setHeader(Context context) {
        TextView disciplineNameView = findViewById(R.id.events_details_discipline_name);
        TextView categoryGenderView = findViewById(R.id.events_details_category_gender_name);
        TextView categoryTypeView = findViewById(R.id.events_details_category_type_name);
        TextView timeView = findViewById(R.id.events_details_time);
        TextView stageView = findViewById(R.id.events_details_stage);


        GenderDisciplinesEntity genderDisciplinesEntity = eventsDTO.getGenderDisciplinesEntity();
        DisciplinesEntity disciplinesEntity = genderDisciplinesEntity.getDisciplinesEntity();
        StagesEntity stagesEntity = eventsDTO.getStagesEntity();

        disciplineNameView.setText(disciplinesEntity.getDisciplinesTEntities().get(0).getName());
        DataBaseEnumsUtils.setGenderTypeToView(context, categoryGenderView, genderDisciplinesEntity);
        categoryTypeView.setText(disciplinesEntity.getIsIndividual()
                ? R.string.events_individual
                : R.string.events_team);

        stageView.setText(stagesEntity.getStagesTEntities().get(0).getStageName());

        String formattedTime = TimeParser.getFormattedOffsetDataTimeFromString(
                eventsDTO.getEventTime(),
                DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
        );
        timeView.setText(formattedTime);
    }

    private void setCompetitorsResults(Context context) {
        ListView competitorsResultsDTOList = findViewById(R.id.events_details_competitors_results_list);
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<CompetitorsResultsDTO>> call = api.getCompetitorsResultsByGameIdAndEventsId(gameId, eventsDTO.getId(), locale);

        call.enqueue(new Callback<List<CompetitorsResultsDTO>>() {
            @Override
            public void onResponse(Call<List<CompetitorsResultsDTO>> call, Response<List<CompetitorsResultsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<CompetitorsResultsDTO> resource = response.body();
                EventsDetailsListAdapter eventsDetailsListAdapter = new EventsDetailsListAdapter(
                        context,
                        R.layout.events_details_list_item,
                        resource);

                competitorsResultsDTOList.setAdapter(eventsDetailsListAdapter);
            }

            @Override
            public void onFailure(Call<List<CompetitorsResultsDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Events query network error");
                call.cancel();
            }
        });

    }

}