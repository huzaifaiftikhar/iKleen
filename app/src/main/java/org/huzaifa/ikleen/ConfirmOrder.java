package org.huzaifa.ikleen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Huzaifa on 16-Jul-17.
 */

public class ConfirmOrder extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Button btnPlaceAnother = findViewById(R.id.btn_place_another);
        btnPlaceAnother.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_place_another:
                Intent intent = new Intent(ConfirmOrder.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}