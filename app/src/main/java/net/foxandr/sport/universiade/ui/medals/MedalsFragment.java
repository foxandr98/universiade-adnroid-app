package net.foxandr.sport.universiade.ui.medals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import net.foxandr.sport.universiade.ui.medals.adapters.MedalsDTOListAdapter;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedalsFragment extends Fragment {

    private static final String ARG_PARAM1 = "locale";

    private String locale;

    public MedalsFragment() {}

    public static MedalsFragment newInstance(String locale) {
        MedalsFragment fragment = new MedalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, locale);
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
        return inflater.inflate(R.layout.fragment_medals, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView medalsDTOListView = view.findViewById(R.id.medals_dto_list);

        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<MedalsDTO>> call = api.getMedalsByLocale(locale);
        call.enqueue(new Callback<List<MedalsDTO>>() {
            @Override
            public void onResponse(Call<List<MedalsDTO>> call, Response<List<MedalsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<MedalsDTO> resource = response.body();
                MedalsDTOListAdapter medalsDTOListAdapter = new MedalsDTOListAdapter(
                        view.getContext(),
                        R.layout.medals_list_item,
                        resource);

                medalsDTOListView.setAdapter(medalsDTOListAdapter);

                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        MedalsDTO selectedMedalsDTO = (MedalsDTO)parent.getItemAtPosition(position);
                        Toast.makeText(view.getContext(), "Был выбран пункт " + selectedMedalsDTO.getCountryName(),
                                Toast.LENGTH_SHORT).show();
                    }
                };
                medalsDTOListView.setOnItemClickListener(itemListener);

            }

            @Override
            public void onFailure(Call<List<MedalsDTO>> call, Throwable t) {
                Log.d("ERROR: ","Sports query network error");
                call.cancel();
            }
        });
    }
}