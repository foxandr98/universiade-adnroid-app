package net.foxandr.sport.universiade.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
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

public class MedalsActivity extends AppCompatActivity {

    private String locale;
    private Long gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medals);
        getSupportActionBar().setTitle(getResources().getString(R.string.medals_table));
        Bundle arguments = getIntent().getExtras();
        locale = arguments.getString("locale");
        gameId = arguments.getLong("gameId");
        setMedalsList(this);
    }

    private void setMedalsList(Activity activity) {

        ListView medalsDTOListView = this.findViewById(R.id.medals_dto_list);
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<MedalsDTO>> call = api.getMedalsByLocale(gameId, locale);
        call.enqueue(new Callback<List<MedalsDTO>>() {
            @Override
            public void onResponse(Call<List<MedalsDTO>> call, Response<List<MedalsDTO>> response) {
                Log.d("TAG", response.code() + "");

                List<MedalsDTO> resource = response.body();
                MedalsDTOListAdapter medalsDTOListAdapter = new MedalsDTOListAdapter(
                        activity,
                        R.layout.medals_list_item,
                        resource);

                medalsDTOListView.setAdapter(medalsDTOListAdapter);
            }

            @Override
            public void onFailure(Call<List<MedalsDTO>> call, Throwable t) {
                Log.d("ERROR: ","Medals query network error");
                call.cancel();
            }
        });

    }
}