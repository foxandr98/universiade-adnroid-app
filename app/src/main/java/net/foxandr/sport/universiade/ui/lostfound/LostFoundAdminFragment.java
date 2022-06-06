package net.foxandr.sport.universiade.ui.lostfound;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.activities.LostfoundFindingAdminActivity;
import net.foxandr.sport.universiade.ui.activities.VenueDetailsActivity;
import net.foxandr.sport.universiade.ui.users.LoggedInUserDTO;

public class LostFoundAdminFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private LoggedInUserDTO loggedInUserDTO;

    public LostFoundAdminFragment() {
    }

    public static LostFoundAdminFragment newInstance(LoggedInUserDTO loggedInUserDTO) {
        LostFoundAdminFragment fragment = new LostFoundAdminFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, loggedInUserDTO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedInUserDTO = (LoggedInUserDTO) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lost_found_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fragment fragment = LostFoundFragment.newInstance(true);
        getChildFragmentManager().beginTransaction()
                .add(R.id.lostfound_admin_common_part, fragment, null)
                .commit();

        Button findItemsButton = view.findViewById(R.id.lostfound_admin_find_items_button);
        findItemsButton.setOnClickListener(
                x -> {
                    Intent itemsIntent = new Intent(view.getContext(), LostfoundFindingAdminActivity.class);
                    itemsIntent.putExtra("loggedInUserDTO", loggedInUserDTO);
                    startActivity(itemsIntent);
                }
        );
    }
}