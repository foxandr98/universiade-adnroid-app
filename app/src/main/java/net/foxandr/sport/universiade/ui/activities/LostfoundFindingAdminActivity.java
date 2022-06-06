package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.lostfound.adapters.LostFoundAdminItemsListAdapter;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTO;
import net.foxandr.sport.universiade.ui.users.LoggedInUserDTO;
import net.foxandr.sport.universiade.utils.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostfoundFindingAdminActivity extends AppCompatActivity {
    private LoggedInUserDTO loggedInUserDTO;

    private LostFoundDTO chosenRequestItem;
    private RadioButton previousChoiceRequestItemRadioButton;

    private LostFoundDTO chosenStockItem;
    private RadioButton previousChoiceStockItemRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfound_finding_admin);
        getSupportActionBar().setTitle("Поиск предметов");
        Bundle argument = getIntent().getExtras();
        loggedInUserDTO = (LoggedInUserDTO) argument.getSerializable("loggedInUserDTO");

        setRequestsList();
        setStoredItemsList();

        Button applyFoundButton = findViewById(R.id.lostfound_admin_items_found_button);
        applyFoundButton.setOnClickListener(
                x -> {
                    if (chosenRequestItem != null)
                        updateIsFound(chosenRequestItem, true);
                    if (chosenStockItem != null)
                        updateIsFound(chosenStockItem, false);
                    Toast.makeText(x.getContext(), "Готово!", Toast.LENGTH_LONG).show();
                }
        );

        SwipeRefreshLayout pullToRefreshRequests = findViewById(R.id.lostfound_admin_request_list_swipe_refresh);
        pullToRefreshRequests.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRequestsList();
                pullToRefreshRequests.setRefreshing(false);
            }
        });

        SwipeRefreshLayout pullToRefreshStock = findViewById(R.id.lostfound_admin_items_stock_list_swipe_refresh);
        pullToRefreshStock.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setStoredItemsList();
                pullToRefreshStock.setRefreshing(false);
            }
        });
    }

    private void setRequestsList() {
        ListView requestList = findViewById(R.id.lostfound_admin_request_list);
        setItems(requestList, true, false);
        requestList.setOnItemClickListener(
                (parent, view, position, id) -> {
                    if (previousChoiceRequestItemRadioButton != null)
                        previousChoiceRequestItemRadioButton.setChecked(false);
                    previousChoiceRequestItemRadioButton = view.findViewById(R.id.lostfound_admin_item_radio_button);
                    previousChoiceRequestItemRadioButton.toggle();
                    chosenRequestItem = (LostFoundDTO) parent.getItemAtPosition(position);
                }
        );
    }

    private void setStoredItemsList() {
        ListView stockList = findViewById(R.id.lostfound_admin_items_stock_list);
        setItems(stockList, false, false);
        stockList.setOnItemClickListener(
                (parent, view, position, id) -> {
                    if (previousChoiceStockItemRadioButton != null)
                        previousChoiceStockItemRadioButton.setChecked(false);
                    previousChoiceStockItemRadioButton = view.findViewById(R.id.lostfound_admin_item_radio_button);
                    previousChoiceStockItemRadioButton.toggle();
                    chosenStockItem = (LostFoundDTO) parent.getItemAtPosition(position);
                }
        );
    }

    private void setItems(ListView lostFoundItems, boolean isRequest, boolean isFound) {
        String token = PasswordEncoder.getAuthToken(
                loggedInUserDTO.getUsername(),
                loggedInUserDTO.getPassword());
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<LostFoundDTO>> call = api.getAdminLostFoundInfo(token, isRequest, isFound);
        call.enqueue(new Callback<List<LostFoundDTO>>() {
            @Override
            public void onResponse(Call<List<LostFoundDTO>> call, Response<List<LostFoundDTO>> response) {
                Log.d("TAG", response.code() + "");
                List<LostFoundDTO> resource = response.body();
                LostFoundAdminItemsListAdapter lostFoundAdminItemsListAdapter = new LostFoundAdminItemsListAdapter(
                        LostfoundFindingAdminActivity.this,
                        R.layout.lostfound_admin_list_item,
                        resource,
                        isRequest);
                lostFoundItems.setAdapter(lostFoundAdminItemsListAdapter);
            }

            @Override
            public void onFailure(Call<List<LostFoundDTO>> call, Throwable t) {
                Log.d("ERROR: ", "Admin lostfound query network error");
                call.cancel();
            }
        });
    }

    private void updateIsFound(LostFoundDTO lostFoundDTO, boolean isRequest) {
        String token = PasswordEncoder.getAuthToken(
                loggedInUserDTO.getUsername(),
                loggedInUserDTO.getPassword());
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<Boolean> call = api.updateLostFoundItemSetIsFound(token, lostFoundDTO.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("TAG", response.code() + "");
                if (isRequest)
                    setRequestsList();
                else
                    setStoredItemsList();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("ERROR: ", "Admin lostfound update isFound query network error");
                call.cancel();
            }
        });
    }

}