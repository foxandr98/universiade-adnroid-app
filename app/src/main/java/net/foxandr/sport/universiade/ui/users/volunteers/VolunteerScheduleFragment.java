package net.foxandr.sport.universiade.ui.users.volunteers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.activities.EventDetailsActivity;
import net.foxandr.sport.universiade.ui.activities.NewsDetailsActivity;
import net.foxandr.sport.universiade.ui.activities.VenueDetailsActivity;
import net.foxandr.sport.universiade.ui.news.adapters.NewsDTOListAdapter;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;
import net.foxandr.sport.universiade.ui.users.LoggedInUserDTO;
import net.foxandr.sport.universiade.ui.users.volunteers.adapters.VolunteersScheduleDTOListAdapter;
import net.foxandr.sport.universiade.utils.PasswordEncoder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerScheduleFragment extends Fragment {

    private static final String ARG_PARAM1 = "locale";
    private static final String ARG_PARAM2 = "loggedInUserDTO";

    private String locale;
    private LoggedInUserDTO loggedInUserDTO;

    public VolunteerScheduleFragment() {
    }


    public static VolunteerScheduleFragment newInstance(String locale, LoggedInUserDTO loggedInUserDTO) {
        VolunteerScheduleFragment fragment = new VolunteerScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, locale);
        args.putSerializable(ARG_PARAM2, loggedInUserDTO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locale = getArguments().getString(ARG_PARAM1);
            loggedInUserDTO = (LoggedInUserDTO) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volunteer_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSchedule(view);

        SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.volunteers_schedule_swipe_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setSchedule(view);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void setSchedule(View view) {
        ListView volunteerScheduleDTOListView = view.findViewById(R.id.volunteers_schedule_dto_list);

        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        String token = PasswordEncoder.getAuthToken(
                loggedInUserDTO.getUsername(),
                loggedInUserDTO.getPassword());
        Call<List<VolunteerScheduleDTO>> call = api.getVolunteerSchedule(
                token,
                loggedInUserDTO.getUsername(),
                locale);
        call.enqueue(new Callback<List<VolunteerScheduleDTO>>() {
            @Override
            public void onResponse(Call<List<VolunteerScheduleDTO>> call, Response<List<VolunteerScheduleDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<VolunteerScheduleDTO> resource = response.body();
                VolunteersScheduleDTOListAdapter scheduleAdapter = new VolunteersScheduleDTOListAdapter(
                        view.getContext(),
                        R.layout.volunteer_schedule_list_item,
                        resource);

                volunteerScheduleDTOListView.setAdapter(scheduleAdapter);

                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        VolunteerScheduleDTO selectedScheduleItem = (VolunteerScheduleDTO) parent.getItemAtPosition(position);

                        Intent venueIntent = new Intent(getContext(), VenueDetailsActivity.class);
                        venueIntent.putExtra("venuesEntity", selectedScheduleItem.getVenuesEntity());
                        startActivity(venueIntent);
                    }
                };
                volunteerScheduleDTOListView.setOnItemClickListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<VolunteerScheduleDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Volunteers schedule query network error");
                call.cancel();
            }
        });

    }
}