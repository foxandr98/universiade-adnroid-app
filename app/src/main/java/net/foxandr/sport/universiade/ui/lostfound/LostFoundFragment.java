package net.foxandr.sport.universiade.ui.lostfound;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.activities.EventsActivity;
import net.foxandr.sport.universiade.ui.home.games.mainsports.SportsDTO;
import net.foxandr.sport.universiade.ui.home.games.mainsports.adapters.SportsDTOListAdapter;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTO;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTOResponse;
import net.foxandr.sport.universiade.utils.TimeParser;

import org.threeten.bp.format.DateTimeFormatter;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LostFoundFragment extends Fragment {

    Uri imageUri;

    public LostFoundFragment() {
    }

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

        Button getImageFromPhone = view.findViewById(R.id.lostfound_get_image_button);
        ImageView imageView = view.findViewById(R.id.lostfound_image);

        EditText describeItemView = view.findViewById(R.id.lostfound_item);
        EditText placeItemView = view.findViewById(R.id.lostfound_place);
        EditText cityItemView = view.findViewById(R.id.lostfound_city);
        EditText nameView = view.findViewById(R.id.lostfound_name);
        EditText contactsView = view.findViewById(R.id.lostfound_contacts);
        Button lostfoundSendButton = view.findViewById(R.id.lostfound_send_button);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    imageView.setImageURI(uri);
                    imageUri = uri;
                });

        getImageFromPhone.setOnClickListener(
                x -> mGetContent.launch("image/*"));


        lostfoundSendButton.setOnClickListener(
                x -> {
                    LostFoundDTO lostFoundDTO = new LostFoundDTO(
                            describeItemView.getText().toString(),
                            placeItemView.getText().toString(),
                            cityItemView.getText().toString(),
                            true,
                            nameView.getText().toString(),
                            contactsView.getText().toString()
                    );

                    String wholeID = DocumentsContract.getDocumentId(imageUri);
                    String id = wholeID.split(":")[1];

                    File image = new File(imageUri.getPath());
                    String s = image.getAbsolutePath();
                    RequestBody requestFile =
                            RequestBody.create(image, MediaType.parse("multipart/form-data"));
                    MultipartBody.Part imageFile =
                            MultipartBody.Part.createFormData("imageFile", image.getName(), requestFile);

                    UniversiadeApi api = UniversiadeService.getInstance().getApi();
                    Call<LostFoundDTOResponse> call = api.postLostfoundRequest(lostFoundDTO, imageFile);
                    call.enqueue(new Callback<LostFoundDTOResponse>() {
                        @Override
                        public void onResponse(Call<LostFoundDTOResponse> call, Response<LostFoundDTOResponse> response) {
                            Log.d("TAG", response.code() + "");
                            LostFoundDTOResponse resource = response.body();

                            String timeSent = TimeParser.getFormattedOffsetDataTimeFromString(
                                    resource.getCreatedOn(),
                                    DateTimeFormatter.ofPattern("HH:mm uuuu-MM-dd")
                            );

                            Toast.makeText(view.getContext(), timeSent,
                                    Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onFailure(Call<LostFoundDTOResponse> call, Throwable t) {
                            Log.d("ERROR: ", "Sports query network error");
                            call.cancel();
                        }
                    });
                }
        );
    }

}