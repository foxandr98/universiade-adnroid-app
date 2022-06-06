package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.lostfound.adapters.LostFoundAdminItemsListAdapter;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTOResponse;
import net.foxandr.sport.universiade.ui.users.LoggedInUserDTO;
import net.foxandr.sport.universiade.utils.PasswordEncoder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostfoundFindingAdminActivity extends AppCompatActivity {
    private LoggedInUserDTO loggedInUserDTO;

    private LostFoundDTOResponse chosenRequestItem;
    private RadioButton previousChoiceRequestItemRadioButton;

    private LostFoundDTOResponse chosenStockItem;
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
                    chosenRequestItem = (LostFoundDTOResponse) parent.getItemAtPosition(position);
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
                    chosenStockItem = (LostFoundDTOResponse) parent.getItemAtPosition(position);
                }
        );
    }

    private void setItems(ListView lostFoundItems, boolean isRequest, boolean isFound) {
        String token = PasswordEncoder.getAuthToken(
                loggedInUserDTO.getUsername(),
                loggedInUserDTO.getPassword());
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<List<LostFoundDTOResponse>> call = api.getAdminLostFoundInfo(token, isRequest, isFound);
        call.enqueue(new Callback<List<LostFoundDTOResponse>>() {
            @Override
            public void onResponse(Call<List<LostFoundDTOResponse>> call, Response<List<LostFoundDTOResponse>> response) {
                Log.d("TAG", response.code() + "");
                List<LostFoundDTOResponse> resource = response.body();
                LostFoundAdminItemsListAdapter lostFoundAdminItemsListAdapter = new LostFoundAdminItemsListAdapter(
                        LostfoundFindingAdminActivity.this,
                        R.layout.lostfound_admin_list_item,
                        resource,
                        isRequest);
                lostFoundItems.setAdapter(lostFoundAdminItemsListAdapter);
            }

            @Override
            public void onFailure(Call<List<LostFoundDTOResponse>> call, Throwable t) {
                Log.d("ERROR: ", "Admin lostfound query network error");
                call.cancel();
            }
        });
    }
}