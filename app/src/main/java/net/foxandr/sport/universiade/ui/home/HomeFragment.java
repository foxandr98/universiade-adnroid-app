package net.foxandr.sport.universiade.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.home.sports.SportsDTO;
import net.foxandr.sport.universiade.ui.home.sports.adapters.SportsDTOListAdapter;
import net.foxandr.sport.universiade.ui.lostfound.LostFoundFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "locale";

    private String locale;

    public HomeFragment() {}

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locale = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView sportsDTOList = view.findViewById(R.id.sports_dto_list);

        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<SportsDTO>> call = api.getSportsByLocale(locale);
        call.enqueue(new Callback<List<SportsDTO>>() {
            @Override
            public void onResponse(Call<List<SportsDTO>> call, Response<List<SportsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<SportsDTO> resource = response.body();
                SportsDTOListAdapter SportsDTOListAdapter = new SportsDTOListAdapter(
                        view.getContext(),
                        R.layout.sports_list_item,
                        resource);

                sportsDTOList.setAdapter(SportsDTOListAdapter);

                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        SportsDTO selectedSportsDTO = (SportsDTO)parent.getItemAtPosition(position);
                        Toast.makeText(view.getContext(), "Был выбран пункт " +
                                        selectedSportsDTO.getSportName(),
                                Toast.LENGTH_SHORT).show();
                    }
                };
                sportsDTOList.setOnItemClickListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<SportsDTO>> call, Throwable t) {
                call.cancel();
            }
        });

    }
}
