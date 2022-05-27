package net.foxandr.sport.universiade.ui.home.games.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.home.games.GamesDTO;

import java.util.List;

public class GamesDTOListAdapter extends ArrayAdapter<GamesDTO> {

    private List<GamesDTO> gamesDTOList;


    public GamesDTOListAdapter(Context context, int resource, List<GamesDTO> gamesDTOList) {
        super(context, resource, gamesDTOList);
        this.gamesDTOList = gamesDTOList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        String fullNameWithCountry = gamesDTOList.get(position).getGameName() +
                ". " + gamesDTOList.get(position).getCountryName() + ".";
        label.setText(fullNameWithCountry);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        String fullNameWithCountry = gamesDTOList.get(position).getGameName() +
                ". " + gamesDTOList.get(position).getCountryName() + ".";
        label.setText(fullNameWithCountry);
        return label;
    }
    
}
