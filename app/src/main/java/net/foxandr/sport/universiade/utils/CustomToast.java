package net.foxandr.sport.universiade.utils;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;

public class CustomToast {

    public static void showCustomToast(Toast toast, int gravityInt, int textSize){
        toast.setGravity(gravityInt, 0, 0);

        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(30);
        toast.show();
    }

}
