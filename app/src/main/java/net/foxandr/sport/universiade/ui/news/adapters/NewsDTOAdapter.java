package net.foxandr.sport.universiade.ui.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

public class NewsDTOAdapter extends ArrayAdapter<NewsDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<NewsDTO> newsDTOList;


    public NewsDTOAdapter(Context context, int resource, List<NewsDTO> newsDTOList) {
        super(context, resource, newsDTOList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.newsDTOList = newsDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView title = view.findViewById(R.id.news_title_name);
        TextView createdOn = view.findViewById(R.id.news_created_on);
//        TextView countryViewName = view.findViewById(R.id.medals_country_name);
//        ImageView flagView = view.findViewById(R.id.medals_flag);
        NewsDTO newsDTO = newsDTOList.get(position);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

//        title.setText(String.valueOf(newsDTO.getNewsTEntities().get(0).getTitle()));
        //createdOn.setText(formatter.format(newsDTO.getCreatedOn()));
        createdOn.setText(newsDTO.getCreatedOn().toString());
        
//        flagView.setImageResource(getContext().getResources().
//                getIdentifier("flag_" + medalsDTO.getIocName().toLowerCase(),
//                        "drawable",
//                        getContext().getPackageName()));

        return view;
    }


}
