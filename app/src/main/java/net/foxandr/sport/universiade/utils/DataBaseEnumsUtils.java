package net.foxandr.sport.universiade.utils;

import android.content.Context;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.home.games.events.genderdisciplines.GenderDisciplinesEntity;

public class DataBaseEnumsUtils {

    public static void setGenderTypeToView(Context context, TextView categoryView, GenderDisciplinesEntity genderDisciplinesEntity) {
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
        categoryView.setText(context.getResources().getString(genderTypeResource));
    }
}
