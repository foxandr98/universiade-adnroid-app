package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.login.data.model.LoggedInUserDTO;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signIn = findViewById(R.id.login_button);
        signIn.setOnClickListener(
                x -> {
                    TextView usernameView = findViewById(R.id.login_username);
                    TextView passwordView = findViewById(R.id.login_password);

                    String username = usernameView.getText().toString();
                    String password = passwordView.getText().toString();
                    String token = getAuthToken(username, password);

                    UniversiadeApi api = UniversiadeService.getInstance().getApi();
                    Call<LoggedInUserDTO> call = api.loginAndGetLoggedInUserDTO(token);

                    call.enqueue(new Callback<LoggedInUserDTO>() {
                        @Override
                        public void onResponse(Call<LoggedInUserDTO> call, Response<LoggedInUserDTO> response) {
                            Log.d("TAG", response.code() + "");
                            if (response.code() == 401) {
                                Toast.makeText(x.getContext(), getResources().getString(R.string.login_error), Toast.LENGTH_LONG).show();
                                usernameView.setError(getResources().getString(R.string.login_error));
                                passwordView.setError(getResources().getString(R.string.login_error));
                            } else {
                                LoggedInUserDTO resource = response.body();
                                Toast.makeText(x.getContext(), getResources().getString(R.string.login_welcome), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoggedInUserDTO> call, Throwable t) {
                            Log.d("ERROR: ", "Authorization query network error");
                            call.cancel();
                        }
                    });

                }
        );
    }

    public static String getAuthToken(String username, String password) {
        byte[] data = new byte[0];
        try {
            data = (username + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }
}