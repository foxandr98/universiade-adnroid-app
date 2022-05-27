package net.foxandr.sport.universiade.ui.lostfound;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.foxandr.sport.universiade.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostFoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostFoundFragment extends Fragment {

    public LostFoundFragment() { }

    public static LostFoundFragment newInstance() {
        return new LostFoundFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lost_found, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

}