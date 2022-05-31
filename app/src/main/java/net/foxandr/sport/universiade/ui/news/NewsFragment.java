package net.foxandr.sport.universiade.ui.news;

import android.content.Intent;
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

import net.foxandr.sport.universiade.ui.activities.EventsActivity;
import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.activities.NewsDetailsActivity;
import net.foxandr.sport.universiade.ui.news.adapters.NewsDTOListAdapter;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "locale";

    private String locale;

    public NewsFragment() {
    }

    public static NewsFragment newInstance(String locale) {
        NewsFragment fragment = new NewsFragment();
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView newsDTOListView = view.findViewById(R.id.news_dto_list);

        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<NewsDTO>> call = api.getNewsByLocale(locale);
        call.enqueue(new Callback<List<NewsDTO>>() {
            @Override
            public void onResponse(Call<List<NewsDTO>> call, Response<List<NewsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<NewsDTO> resource = response.body();
                NewsDTOListAdapter newsDTOListAdapter = new NewsDTOListAdapter(
                        view.getContext(),
                        R.layout.news_list_item,
                        resource);

                newsDTOListView.setAdapter(newsDTOListAdapter);

                AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        NewsDTO selectedNewsDTO = (NewsDTO) parent.getItemAtPosition(position);
                        Toast.makeText(view.getContext(), getResources().getString(R.string.you_chose) +
                                        selectedNewsDTO.getNewsTEntities().get(0).getTitle(),
                                Toast.LENGTH_SHORT).show();

                        Intent newsDetails = new Intent(getActivity(), NewsDetailsActivity.class);
                        newsDetails.putExtra("locale", locale);
                        newsDetails.putExtra("newsDTO", selectedNewsDTO);
                        startActivity(newsDetails);
                    }
                };
                newsDTOListView.setOnItemClickListener(itemListener);
            }

            @Override
            public void onFailure(Call<List<NewsDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Sports query network error");
                call.cancel();
            }
        });

    }



}