package net.foxandr.sport.universiade.ui.home.games.mainsports.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.home.games.mainsports.SportsDTO;

import java.util.List;

public class SportsDTOListAdapter extends ArrayAdapter<SportsDTO> {


    private LayoutInflater inflater;
    private int layout;
    private List<SportsDTO> sportsDTOList;


    public SportsDTOListAdapter(Context context, int resource, List<SportsDTO> SportsDTOList) {
        super(context, resource, SportsDTOList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.sportsDTOList = SportsDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView imageView = view.findViewById(R.id.sport_icon);
        TextView nameView = view.findViewById(R.id.sport_name);

        SportsDTO sportsDTO = sportsDTOList.get(position);

        imageView.setImageResource(getContext().getResources().
                getIdentifier("sport_" + sportsDTO.getSportCode().toLowerCase(),
                        "drawable",
                        getContext().getPackageName()));
        nameView.setText(sportsDTO.getSportName());
        view.setBackgroundColor(position % 2 == 0
                ? Color.WHITE
                : Color.parseColor("#DEDEDE"));
        return view;
    }
    
}
