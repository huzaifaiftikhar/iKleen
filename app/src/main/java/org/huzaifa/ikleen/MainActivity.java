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

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, homeFragment).commit();

        bottomMenu.setOnItemClickListener(new BottomMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                switch (position) {
                    case 0:
                        FlipCode.value = 0;
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, homeFragment).commit();
                        break;
                    case 1:
                        FlipCode.value = 1;
                        MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, myOrdersFragment).commit();
                        break;
                    case 2:
                        FlipCode.value = 2;
                        ProfileFragment loginFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, loginFragment).commit();
                        break;
                    case 3:
                        FlipCode.value = 3;
                        PricesFragment pricesFragment = new PricesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, pricesFragment).commit();
                        break;
                    case 4:
                        FlipCode.value = 4;
                        HelpFragment helpFragment = new HelpFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, helpFragment).commit();
                        break;
                }
            }
        });
    }
}