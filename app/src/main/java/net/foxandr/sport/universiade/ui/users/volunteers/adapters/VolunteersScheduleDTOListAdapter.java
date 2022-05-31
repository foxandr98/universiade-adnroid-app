package net.foxandr.sport.universiade.ui.users.volunteers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;
import net.foxandr.sport.universiade.ui.users.volunteers.VolunteerScheduleDTO;
import net.foxandr.sport.universiade.utils.TimeParser;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

public class VolunteersScheduleDTOListAdapter extends ArrayAdapter<VolunteerScheduleDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<VolunteerScheduleDTO> volunteersScheduleDTOList;


    public VolunteersScheduleDTOListAdapter(Context context, int resource, List<VolunteerScheduleDTO> volunteersScheduleDTOList) {
        super(context, resource, volunteersScheduleDTOList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.volunteersScheduleDTOList = volunteersScheduleDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView timeView = view.findViewById(R.id.volunteers_schedule_time);
        TextView venueTitleView = view.findViewById(R.id.volunteers_schedule_venue_title);
        TextView extraInfoView = view.findViewById(R.id.volunteers_schedule_extra_info);

        VolunteerScheduleDTO scheduleDTO = volunteersScheduleDTOList.get(position);

        String formattedTime = TimeParser.getFormattedOffsetDataTimeFromString(
                scheduleDTO.getUtcHelpTime(),
                DateTimeFormatter.ofPattern("HH:mm \nuuuu-MM-dd")
        );
        timeView.setText(formattedTime);

        venueTitleView.setText(String.valueOf(scheduleDTO.getVenuesEntity().getVenuesTEntities().get(0).getVenueName()));
        extraInfoView.setText(String.valueOf(scheduleDTO.getExtraInfo()));

        view.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#DEDEDE"));
        return view;
    }
}
