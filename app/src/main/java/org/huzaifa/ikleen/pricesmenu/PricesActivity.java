package org.huzaifa.ikleen.pricesmenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.huzaifa.ikleen.HelpActivity;
import org.huzaifa.ikleen.LoginActivity;
import org.huzaifa.ikleen.Main2Activity;
import org.huzaifa.ikleen.MainActivity;
import org.huzaifa.ikleen.R;
import org.huzaifa.ikleen.bottommenu.BottomMenu;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PricesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PricesJSONModel> data;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ikleen_logo3);

        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://androidtest789.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponseModel> call = request.getJSON();

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PricesActivity.this);
        progressDialog.setMessage("Loading Prices....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show(); // show it

        call.enqueue(new Callback<JSONResponseModel>() {
            @Override
            public void onResponse(Call<JSONResponseModel> call, Response<JSONResponseModel> response) {
                progressDialog.dismiss();
                JSONResponseModel jsonResponseModel = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponseModel.getPrice()));
                adapter = new DataAdapter(getApplicationContext(), data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(PricesActivity.this, "No Network!", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());

                final AlertDialog.Builder builder = new AlertDialog.Builder(PricesActivity.this, R.style.DialogTheme);
                builder.setTitle("No Connection!");
                builder.setMessage("Please check your connection and try again.");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }
        });
    }
}
