package org.huzaifa.ikleen.pricesmenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.huzaifa.ikleen.FlipCode;
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

public class PricesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<PricesJSONModel> data;
    private DataAdapter adapter;
    ProgressDialog progressDialog;
    Context context;
    Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prices, container, false);
        context = getActivity().getApplicationContext();
        activity = getActivity();
        initViews(v);
        return v;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    @Override
    public void onDetach() {
        if (progressDialog != null)
            progressDialog.dismiss();
        super.onDetach();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://androidtest789.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponseModel> call = request.getJSON();

        progressDialog = new ProgressDialog(activity);
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
                //adapter = new DataAdapter(getActivity().getApplicationContext(), data);
                adapter = new DataAdapter(context, data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(PricesActivity.this, "No Network!", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());

                final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
                builder.setTitle("No Connection!");
                builder.setMessage("Please check your connection and try again.");
                builder.setCancelable(false);
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadJSON();
                    }
                });
                builder.setNegativeButton("Continue offline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancel the dialog.
                        //Snackbar.make(getWindow().getDecorView().getRootView(),"Prices can't be fetched offline.\nOrders can't be placed offline.",Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getActivity().getApplicationContext(),"Prices can't be fetched offline. Orders can't be placed offline.", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Prices can't be fetched offline. Orders can't be placed offline.", Toast.LENGTH_LONG).show();
                    }
                });
                if (builder != null)
                    builder.show();
            }
        });
    }
}
