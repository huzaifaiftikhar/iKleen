package org.huzaifa.ikleen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.huzaifa.ikleen.bottommenu.BottomMenu;
import org.huzaifa.ikleen.pricesmenu.PricesActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private EditText etLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Button btnPickup;
        btnPickup = v.findViewById(R.id.btn_schedule_pickup);
        btnPickup.setOnClickListener(this);

        etLocation = v.findViewById(R.id.editText);

        return v;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_schedule_pickup:
                Intent pickup = new Intent(getActivity(), Main2Activity.class);
                pickup.putExtra("location", etLocation.getText().toString());
                startActivity(pickup);
                break;
        }

    }
}