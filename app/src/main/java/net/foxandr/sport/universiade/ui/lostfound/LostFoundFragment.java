package net.foxandr.sport.universiade.ui.lostfound;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.PartMap;


public class LostFoundFragment extends Fragment {

    Uri imageUri;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

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
                x -> {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions( //Method of Fragment
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        );
                    } else {
                        mGetContent.launch("image/*");
                    }
                });


        lostfoundSendButton.setOnClickListener(
                x -> {
                    MultipartBody.Part imageFile = prepareFileTOSend(x.getContext(), imageUri);

                    Map<String, String> lostFoundDTO = new HashMap<>();
                    lostFoundDTO.put("itemDescription", describeItemView.getText().toString());
                    lostFoundDTO.put("lostItemArea", placeItemView.getText().toString());
                    lostFoundDTO.put("cityName", cityItemView.getText().toString());
                    lostFoundDTO.put("isRequest", "true");
                    lostFoundDTO.put("contactName", nameView.getText().toString());
                    lostFoundDTO.put("contactToNotify", contactsView.getText().toString());

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

                            Toast.makeText(view.getContext(),R.string.lostfound_success+timeSent, Toast.LENGTH_SHORT).show();
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

    public static RequestBody toRequestBody (String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }

    public static MultipartBody.Part prepareFileTOSend(Context context, Uri imageUri){
        String wholeID = DocumentsContract.getDocumentId(imageUri);
        String id = wholeID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
        String filePath = "";
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        File image = new File(filePath);

        RequestBody requestFile =
                RequestBody.create(image, MediaType.parse("multipart/form-data"));
        MultipartBody.Part imageFile =
                MultipartBody.Part.createFormData("imageFile", image.getName(), requestFile);

        return imageFile;
    }

}