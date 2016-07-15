package com.vaporwarecorp.restdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
// ------------------------------ FIELDS ------------------------------

    private Api mApi;
    private TextView mDisplayView;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplayView = (TextView) findViewById(R.id.display_view);

        findViewById(R.id.refresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshJoke();
            }
        });

        initializeRetrofit();
        refreshJoke();
    }

    private void initializeRetrofit() {
        mApi = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);
    }

    private void refreshJoke() {
        mApi.randomJoke().enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                mDisplayView.setText(response.body().value);
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                mDisplayView.setText(t.getMessage());
            }
        });
    }

    interface Api {
        @GET("/jokes/random")
        Call<Joke> randomJoke();
    }
}
