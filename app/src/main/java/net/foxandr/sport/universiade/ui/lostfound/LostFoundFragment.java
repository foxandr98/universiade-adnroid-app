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

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LostFoundFragment extends Fragment {

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
        Button lostfoundButton = view.findViewById(R.id.lostfound_send_button);
        ImageView imageView = view.findViewById(R.id.lostfound_image);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> imageView.setImageURI(uri));

        getImageFromPhone.setOnClickListener(
                x -> mGetContent.launch("image/*"));



    }

}