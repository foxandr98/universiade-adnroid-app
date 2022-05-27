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
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextColor(Color.BLACK);

        String fullName = gamesDTOList.get(position).getGameName();

        textView.setText(fullName);
        textView.setBackgroundColor(position % 2 == 0
                ? Color.parseColor("#F5F5F5")
                : Color.parseColor("#DEDEDE"));

        return textView;
    }
}
