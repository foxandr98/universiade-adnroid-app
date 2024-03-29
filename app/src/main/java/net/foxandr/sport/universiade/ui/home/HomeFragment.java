package net.foxandr.sport.universiade.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.foxandr.sport.universiade.ui.activities.EventsActivity;
import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.activities.MedalsActivity;
import net.foxandr.sport.universiade.ui.home.games.GamesDTO;
import net.foxandr.sport.universiade.ui.home.games.adapters.GamesDTOListAdapter;
import net.foxandr.sport.universiade.ui.home.games.sports.SportsDTO;
import net.foxandr.sport.universiade.ui.home.games.sports.adapters.SportsDTOListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "locale";

    private String locale;
    private Long currentGameId;


    public HomeFragment() {
    }

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
        setGames(view);

        SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.home_refresh_swipe);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setGames(view);
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    public void setGames(View view) {
        Spinner gamesDTOListSpinnerView = view.findViewById(R.id.games_spinner);
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<GamesDTO>> call = api.getGamesByLocale(locale);

        call.enqueue(new Callback<List<GamesDTO>>() {
            @Override
            public void onResponse(Call<List<GamesDTO>> call, Response<List<GamesDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<GamesDTO> resource = response.body();
                GamesDTOListAdapter SportsDTOListAdapter = new GamesDTOListAdapter(
                        view.getContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        resource);
                gamesDTOListSpinnerView.setAdapter(SportsDTOListAdapter);

                setGamesInfo(view, resource.get(0));

//                getSports(view, resource.get(0).getGameId());

                gamesDTOListSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                        GamesDTO selectedSportsDTO = (GamesDTO) parent.getItemAtPosition(position);
                        setGamesInfo(view, selectedSportsDTO);
                        getSports(view, resource.get(position).getGameId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                Button medalsButtonView = view.findViewById(R.id.medals_get_button);
                medalsButtonView.setOnClickListener(
                        (x) -> {
                            Intent intent = new Intent(getActivity(), MedalsActivity.class);
                            intent.putExtra("locale", locale);
                            intent.putExtra("gameId", currentGameId);
                            startActivity(intent);
                        }
                );
            }

            @Override
            public void onFailure(Call<List<GamesDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Games query network error");
                call.cancel();
            }
        });

    }


    public void getSports(View view, Long gameId) {
        ListView sportsDTOListView = view.findViewById(R.id.sports_dto_list);
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<SportsDTO>> call = api.getSportsByGameIdAndLocale(gameId, locale);
        call.enqueue(new Callback<List<SportsDTO>>() {
            @Override
            public void onResponse(Call<List<SportsDTO>> call, Response<List<SportsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<SportsDTO> resource = response.body();
                SportsDTOListAdapter sportsDTOListAdapter = new SportsDTOListAdapter(
                        view.getContext(),
                        R.layout.sports_list_item,
                        resource);

                sportsDTOListView.setAdapter(sportsDTOListAdapter);

                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        SportsDTO selectedSportsDTO = (SportsDTO) parent.getItemAtPosition(position);
                        Intent eventsActivity = new Intent(getActivity(), EventsActivity.class);
                        eventsActivity.putExtra("sportsDTO", selectedSportsDTO);
                        eventsActivity.putExtra("gameId", gameId);
                        eventsActivity.putExtra("locale", locale);
                        startActivity(eventsActivity);
                    }
                };
                sportsDTOListView.setOnItemClickListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<SportsDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Sports query network error");
                call.cancel();
            }
        });
    }


    private void setGamesInfo(View view, GamesDTO gameInfo){
        TextView gamesNameView = view.findViewById(R.id.games_name);
        TextView gamesCountry = view.findViewById(R.id.games_country);
        ImageView gamesDTOIconView = view.findViewById(R.id.games_season_icon);

        gamesCountry.setText(gameInfo.getCountryName());
        String gameFullName = String.format("%s. \n%s: %s", gameInfo.getGameName(),
                getResources().getString(R.string.lostfound_city),
                gameInfo.getCityName());
        gamesNameView.setText(gameFullName);
        boolean isSummer = gameInfo.getIsSummer();
        gamesDTOIconView.setImageResource(isSummer ? R.drawable.games_summer : R.drawable.games_winter);
        currentGameId = gameInfo.getGameId();
    }


}
