package net.foxandr.sport.universiade.ui.home.games.events.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.home.games.events.competitors.CompetitorsResultsDTO;
import net.foxandr.sport.universiade.ui.home.games.participants.ParticipantsEntity;
import net.foxandr.sport.universiade.ui.home.games.participants.universities.UniversitiesEntity;

import java.util.List;


public class EventsDetailsListAdapter extends ArrayAdapter<CompetitorsResultsDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<CompetitorsResultsDTO> competitorsResultsDTOList;


    public EventsDetailsListAdapter(Context context, int resource, List<CompetitorsResultsDTO> competitorsResultsDTOList) {
        super(context, resource, competitorsResultsDTOList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.competitorsResultsDTOList = competitorsResultsDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        CompetitorsResultsDTO competitorsResultsDTO = competitorsResultsDTOList.get(position);
        setParticipantsInfo(view, competitorsResultsDTO);

        TextView resultNameView = view.findViewById(R.id.events_details_result);
        CompetitorsResultsDTO.ResultTypesEntity resultTypesEntity = competitorsResultsDTO.getResultTypesEntity();
        resultNameView.setText(resultTypesEntity.getResultTypesTEntities().get(0).getName());

        view.setBackgroundColor(position % 2 == 0
                ? Color.WHITE
                : Color.parseColor("#DEDEDE"));
        return view;
    }

    private void setParticipantsInfo(View view, CompetitorsResultsDTO competitorsResultsDTO) {
        TextView firstNameView = view.findViewById(R.id.events_details_athlete_first_name);
        TextView lastNameView = view.findViewById(R.id.events_details_athlete_last_name);
        TextView middleNameView = view.findViewById(R.id.events_details_athlete_middle_name);
        ImageView countryImageView = view.findViewById(R.id.events_details_country);
        TextView universityAbbrView = view.findViewById(R.id.events_details_university);


        ParticipantsEntity participantsEntity = competitorsResultsDTO.getParticipantsEntity();
        ParticipantsEntity.AthletesEntity athletesEntity = participantsEntity.getAthletesEntity();
        ParticipantsEntity.AthletesEntity.AthletesTEntity athletesTEntity = athletesEntity.getAthletesTEntities().get(0);
        UniversitiesEntity universitiesEntity = participantsEntity.getUniversitiesEntity();

        firstNameView.setText(athletesTEntity.getFirstName());
        lastNameView.setText(athletesTEntity.getLastName());
        middleNameView.setText(athletesTEntity.getMiddleName());
        universityAbbrView.setText(universitiesEntity.getUniversitiesTEntities().get(0).getAbbreviation());

        countryImageView.setImageResource(getContext().getResources().
                getIdentifier("flag_" + universitiesEntity
                                .getCountriesEntity()
                                .getIocName()
                                .toLowerCase(),
                        "drawable",
                        getContext().getPackageName()));
    }
}
