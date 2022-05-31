package net.foxandr.sport.universiade.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class UniversiadeService {

    private static UniversiadeService instance;

    public static UniversiadeService getInstance(){
        if(instance == null){
            instance = new UniversiadeService();
        }
        return instance;
    }

    public static final String API_URL = "http://85.115.189.14:60000/api/v1/";
    UniversiadeApi api;

    private UniversiadeService() {
        Retrofit retrofit = createRetrofit();
        api = retrofit.create(UniversiadeApi.class);
    }

    public UniversiadeApi getApi() {
        return api;
    }


    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }


    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
    }
}
