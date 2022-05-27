package net.foxandr.sport.universiade.ui.medals.adapters;

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

import java.util.List;

public class MedalsDTOListAdapter extends ArrayAdapter<MedalsDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<MedalsDTO> medalsDTOs;


    public MedalsDTOListAdapter(Context context, int resource, List<MedalsDTO> medalsDTOs) {
        super(context, resource, medalsDTOs);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.medalsDTOs = medalsDTOs;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView rankNameView = view.findViewById(R.id.medals_rank);
        ImageView flagView = view.findViewById(R.id.medals_flag);
        TextView countryNameView= view.findViewById(R.id.medals_country_name);
        TextView iocNameView = view.findViewById(R.id.medals_ioc_name);
        TextView goldCountView = view.findViewById(R.id.medals_gold_count);
        TextView silverCountView = view.findViewById(R.id.medals_silver_count);
        TextView bronzeCountView = view.findViewById(R.id.medals_bronze_count);
        TextView totalCountView = view.findViewById(R.id.medals_total_count);

        MedalsDTO medalsDTO = medalsDTOs.get(position);

        rankNameView.setText(String.valueOf(medalsDTO.getRank()));
        flagView.setImageResource(getContext().getResources().
                getIdentifier("flag_" + medalsDTO.getIocName().toLowerCase(),
                        "drawable",
                        getContext().getPackageName()));
        countryNameView.setText(medalsDTO.getCountryName());
        iocNameView.setText(medalsDTO.getIocName());
        goldCountView.setText(String.valueOf(medalsDTO.getGoldCount()));
        silverCountView.setText(String.valueOf(medalsDTO.getSilverCount()));
        bronzeCountView.setText(String.valueOf(medalsDTO.getBronzeCount()));
        totalCountView.setText(String.valueOf(medalsDTO.getTotal()));

        view.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#DEDEDE"));
        return view;
    }

}
