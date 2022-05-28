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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.activities.EventsActivity;
import net.foxandr.sport.universiade.ui.home.games.mainsports.SportsDTO;
import net.foxandr.sport.universiade.ui.home.games.mainsports.adapters.SportsDTOListAdapter;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTO;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTOResponse;
import net.foxandr.sport.universiade.utils.CustomToast;
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

    EditText describeItemView;
    EditText placeItemView;
    EditText cityItemView;
    EditText nameView;
    EditText contactsView;
    Button lostfoundSendButton;

    boolean isFilled = true;
    MultipartBody.Part imageFile;

    Map<String, String> lostFoundDTO;

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

        describeItemView = view.findViewById(R.id.lostfound_item);
        placeItemView = view.findViewById(R.id.lostfound_place);
        cityItemView = view.findViewById(R.id.lostfound_city);
        nameView = view.findViewById(R.id.lostfound_name);
        contactsView = view.findViewById(R.id.lostfound_contacts);
        lostfoundSendButton = view.findViewById(R.id.lostfound_send_button);

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
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        );
                    } else {
                        mGetContent.launch("image/*");
                    }
                });


        lostfoundSendButton.setOnClickListener(
                x -> {
                    if (!(isAllFieldFilled((ViewGroup) getView()) && imageUri != null)) {
                        Toast.makeText(
                                view.getContext(),
                                getResources().getString(R.string.lostfound_bad),
                                Toast.LENGTH_SHORT).show();
                        isFilled = true;
                        return;
                    }

                    imageFile = prepareFileTOSend(x.getContext(), imageUri);

                    lostFoundDTO = new HashMap<>();
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


                            Toast toast = Toast.makeText(
                                    view.getContext(),
                                    getResources().getString(R.string.lostfound_success) + timeSent,
                                    Toast.LENGTH_SHORT
                            );
                            CustomToast.showCustomToast(toast, Gravity.CENTER, 30);

                            clearForms((ViewGroup)getView());
                            clearFields();
                        }

                        @Override
                        public void onFailure(Call<LostFoundDTOResponse> call, Throwable t) {
                            Log.d("ERROR: ", "Lostfound query network error");
                            call.cancel();
                        }
                    });
                }
        );
    }


    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("text/plain"));
    }

    public static MultipartBody.Part prepareFileTOSend(Context context, Uri imageUri) {
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

    private void clearForms(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(0);
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForms((ViewGroup) view);
        }
    }

    private boolean isAllFieldFilled(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                TextView textView = (EditText) view;
                if (TextUtils.isEmpty(textView.getText().toString())) {
                    textView.setError(getResources().getString(R.string.lostfound_bad_fill_data));
                    isFilled = false;
                }
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                isAllFieldFilled((ViewGroup) view);
        }
        return isFilled;
    }

    private void clearFields() {
        imageFile = null;
        lostFoundDTO = null;
        imageUri = null;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

}