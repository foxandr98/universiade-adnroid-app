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
import net.foxandr.sport.universiade.ui.home.games.events.EventsDTO;
import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.GenderDisciplinesEntity;
import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.disciplines.DisciplinesEntity;
import net.foxandr.sport.universiade.ui.home.games.events.stages.StagesEntity;
import net.foxandr.sport.universiade.utils.TimeParser;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;


public class EventsDTOListAdapter extends ArrayAdapter<EventsDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<EventsDTO> eventsDTOList;


    public EventsDTOListAdapter(Context context, int resource, List<EventsDTO> eventsDTOList) {
        super(context, resource, eventsDTOList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.eventsDTOList = eventsDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.events_discipline);
        TextView categoryGender = view.findViewById(R.id.events_category_gender);
        TextView categoryType = view.findViewById(R.id.events_category_is_individual);
        TextView stageView = view.findViewById(R.id.events_stage);
        TextView timeView = view.findViewById(R.id.events_time);
        ImageView completeView = view.findViewById(R.id.events_is_ended);

        EventsDTO eventsDTO = eventsDTOList.get(position);

        GenderDisciplinesEntity genderDisciplinesEntity = eventsDTO.getGenderDisciplinesEntity();
        DisciplinesEntity disciplinesEntity = genderDisciplinesEntity.getDisciplinesEntity();
        StagesEntity stagesEntity = eventsDTO.getStagesEntity();

        nameView.setText(disciplinesEntity.getDisciplinesTEntities().get(0).getName());
        setGenderType(categoryGender, genderDisciplinesEntity);
        categoryType.setText(disciplinesEntity.getIsIndividual()
                ? R.string.events_individual
                : R.string.events_team);

        stageView.setText(stagesEntity.getStagesTEntities().get(0).getStageName());

        String formattedTime = TimeParser.getFormattedOffsetDataTimeFromString(
                eventsDTO.getEventTime(),
                DateTimeFormatter.ofPattern("HH:mm uuuu-MM-dd")
        );
        timeView.setText(formattedTime);

        if(eventsDTO.getFinished())
            completeView.setImageResource(R.drawable.events_complete);

        view.setBackgroundColor(position % 2 == 0
                ? Color.WHITE
                : Color.parseColor("#DEDEDE"));
        return view;
    }


    private void setGenderType(TextView categoryView, GenderDisciplinesEntity genderDisciplinesEntity) {
        int genderTypeResource;
        switch (genderDisciplinesEntity.getGenderType()) {
            case "m":
                genderTypeResource = R.string.events_men;
                break;
            case "f":
                genderTypeResource = R.string.events_women;
                break;
            default:
                genderTypeResource = R.string.events_mixed;
                break;
        }
        categoryView.setText(getContext().getResources().getString(genderTypeResource));
    }

}
