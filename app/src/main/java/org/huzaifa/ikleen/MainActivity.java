package org.huzaifa.ikleen;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.huzaifa.ikleen.bottommenu.BottomMenu;
import org.huzaifa.ikleen.pricesmenu.PricesFragment;

public class MainActivity extends AppCompatActivity {

    /*private EditText etLocation;*/
    BottomMenu bottomMenu;

    @Override
    public void onResume() {
        super.onResume();
        bottomMenu = findViewById(R.id.bottom_menu);
        bottomMenu.select(FlipCode.value);
    }

    @Override
    public void onBackPressed() {
        if (FlipCode.card1_flipped) {
            FlipAnimation flipAnimation = new FlipAnimation(FlipCode.cardFace, FlipCode.cardBack);
            flipAnimation.reverse();
            FlipCode.rootLayout.startAnimation(flipAnimation);
            FlipCode.card1_flipped = false;
        } else if (FlipCode.card2_flipped) {
            FlipAnimation flipAnimation = new FlipAnimation(FlipCode.cardFace2, FlipCode.cardBack2);
            flipAnimation.reverse();
            FlipCode.card2_flipped = false;
            FlipCode.rootLayout2.startAnimation(flipAnimation);
        } else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button btnPickup;*/
        bottomMenu = (BottomMenu) findViewById(R.id.bottom_menu);
        /*btnPickup = (Button) findViewById(R.id.btn_schedule_pickup);*/
        /*etLocation = (EditText) findViewById(R.id.editText);*/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setTitle("");
        actionBar.setIcon(R.drawable.ikleen_logo3);

        FlipCode.card1_flipped = FlipCode.card2_flipped = false;
        FlipCode.value = 0;
        bottomMenu.select(FlipCode.value); //default page is home
        /*btnPickup.setOnClickListener(this);*/

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, mainFragment).commit();

        bottomMenu.setOnItemClickListener(new BottomMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                switch (position) {
                    case 0:
                        FlipCode.value = 0;
                        MainFragment mainFragment = new MainFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, mainFragment).commit();
                        break;
                    case 1:
                        /*Intent pickup = new Intent(MainActivity.this, Main2Activity.class);
                        //pickup.putExtra("location", etLocation.getText().toString());
                        startActivity(pickup);
                        break;*/
                        FlipCode.value = 1;
                        RequestFragment requestFragment = new RequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, requestFragment).commit();
                        break;
                    case 2:
                        /*Intent profile = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(profile);
                        break;*/
                        FlipCode.value = 2;
                        ProfileFragment loginFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, loginFragment).commit();
                        break;
                    case 3:
                        /*Intent prices = new Intent(MainActivity.this,PricesActivity.class);
                        startActivity(prices);
                        break;*/
                        FlipCode.value = 3;
                        PricesFragment pricesFragment = new PricesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, pricesFragment).commit();
                        break;
                    case 4:
                        /*Intent help = new Intent(MainActivity.this,HelpActivity.class);
                        startActivity(help);
                        break;*/
                        FlipCode.value = 4;
                        HelpFragment helpFragment = new HelpFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, helpFragment).commit();
                        break;
                }
            }
        });
    }
}