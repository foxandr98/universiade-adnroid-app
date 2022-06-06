package net.foxandr.sport.universiade.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDownloader {

    public static void getImageByUuid(ImageView imageView, String uuid) {
        UniversiadeApi api = UniversiadeService.getInstance().getApi();
        Call<ResponseBody> call = api.getImageBytesByUuid(uuid);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", response.code() + "");
                ResponseBody resource = response.body();

                byte[] imageBytes = new byte[0];
                try {
                    imageBytes = resource.bytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ERROR: ", "News image query network error");
                call.cancel();
            }
        });
    }

}
