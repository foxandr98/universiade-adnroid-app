package net.foxandr.sport.universiade.ui.news.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;
import net.foxandr.sport.universiade.utils.TimeParser;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.zone.ZoneRules;

import java.util.List;

public class NewsDTOListAdapter extends ArrayAdapter<NewsDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<NewsDTO> newsDTOList;


    public NewsDTOListAdapter(Context context, int resource, List<NewsDTO> newsDTOList) {
        super(context, resource, newsDTOList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.newsDTOList = newsDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView titleView = view.findViewById(R.id.news_title_name);
        TextView createdOnView = view.findViewById(R.id.news_created_on);
        NewsDTO newsDTO = newsDTOList.get(position);

        titleView.setText(String.valueOf(newsDTO.getNewsTEntities().get(0).getTitle()));

        String formattedTime = TimeParser.getFormattedOffsetDataTimeFromString(
                newsDTO.getCreatedOn(),
                DateTimeFormatter.ofPattern("HH:mm \nuuuu-MM-dd")
        );
        createdOnView.setText(formattedTime);

        view.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#DEDEDE"));
        return view;
    }


}
