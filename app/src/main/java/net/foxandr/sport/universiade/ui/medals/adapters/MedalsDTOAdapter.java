package net.foxandr.sport.universiade.ui.medals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;

import java.util.List;

public class MedalsDTOAdapter extends ArrayAdapter<MedalsDTO> {

    private LayoutInflater inflater;
    private int layout;
    private List<MedalsDTO> medalsDTOs;


    public MedalsDTOAdapter(Context context, int resource, List<MedalsDTO> medalsDTOs) {
        super(context, resource, medalsDTOs);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.medalsDTOs = medalsDTOs;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView rankName = view.findViewById(R.id.medals_rank);
        ImageView flagView = view.findViewById(R.id.medals_flag);
        TextView countryViewName = view.findViewById(R.id.medals_country_name);
        TextView iocName = view.findViewById(R.id.medals_ioc_name);
        TextView goldCount = view.findViewById(R.id.medals_gold_count);
        TextView silverCount = view.findViewById(R.id.medals_silver_count);
        TextView bronzeCount = view.findViewById(R.id.medals_bronze_count);
        TextView totalCount = view.findViewById(R.id.medals_total_count);

        MedalsDTO medalsDTO = medalsDTOs.get(position);

        rankName.setText(String.valueOf(medalsDTO.getRank()));
//        flagView.setImageResource(medalsDTO.getFlagResource());
        countryViewName.setText(medalsDTO.getCountryName());
        iocName.setText(medalsDTO.getIocName());
        goldCount.setText(String.valueOf(medalsDTO.getGoldCount()));
        silverCount.setText(String.valueOf(medalsDTO.getSilverCount()));
        bronzeCount.setText(String.valueOf(medalsDTO.getBronzeCount()));
        totalCount.setText(String.valueOf(medalsDTO.getTotal()));

        return view;
    }

}
