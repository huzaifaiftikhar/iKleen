package org.huzaifa.ikleen;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.huzaifa.ikleen.pricesmenu.PricesActivity;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText etAddress;
    private Button btn;
    private int flipcode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnPickupTime;
        Button btnDeliveryTime;
        Button btnSchedulePickup;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        etAddress = findViewById(R.id.address);

        btnPickupTime = findViewById(R.id.btn_pickup_time);
        btnDeliveryTime = findViewById(R.id.btn_delivery_time);
        btnSchedulePickup = findViewById(R.id.btn_schedule_pickup);


        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String str = intent.getStringExtra("location");
            etAddress.setText(str);
        }

        btnPickupTime.setOnClickListener(this);
        btnDeliveryTime.setOnClickListener(this);
        btnSchedulePickup.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_prices) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent home = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(home);
            finish();

        } else if (id == R.id.nav_request) {

        } else if (id == R.id.nav_login) {
            //flipcode = 1;
            FlipCode.value = 1;
            Intent profile = new Intent(Main2Activity.this, LoginActivity.class);
            //home.putExtra("flipcode", flipcode);
            startActivity(profile);
            finish();

        } else if (id == R.id.nav_price) {
            Intent price = new Intent(Main2Activity.this, PricesActivity.class);
            startActivity(price);
            finish();

        } else if (id == R.id.nav_contact) {
            Intent help = new Intent(Main2Activity.this, HelpActivity.class);
            startActivity(help);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pickup_time:
                set(R.id.btn_pickup_time);
                break;
            case R.id.btn_delivery_time:
                set(R.id.btn_delivery_time);
                break;
            case R.id.btn_schedule_pickup:
                Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }
    }

    //function to set the time
    public void setTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Main2Activity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedHour > 11) {
                    if (selectedHour != 12) {
                        selectedHour %= 12;
                    }
                    if (selectedHour < 10 && selectedMinute < 10) {
                        btn.setText(btn.getText().toString() + "   0" + selectedHour + ":0" + selectedMinute + " PM");
                    } else if (selectedHour < 10) {
                        btn.setText(btn.getText().toString() + "   0" + selectedHour + ":" + selectedMinute + " PM");
                    } else if (selectedMinute < 10) {
                        btn.setText(btn.getText().toString() + "   " + selectedHour + ":0" + selectedMinute + " PM");
                    } else {
                        btn.setText(btn.getText().toString() + "   " + selectedHour + ":" + selectedMinute + " PM");
                    }
                } else {
                    if (selectedHour < 10 && selectedMinute < 10) {
                        btn.setText(btn.getText().toString() + "   0" + selectedHour + ":0" + selectedMinute + " AM");
                    } else if (selectedHour < 10) {
                        btn.setText(btn.getText().toString() + "   0" + selectedHour + ":" + selectedMinute + " AM");
                    } else if (selectedMinute < 10) {
                        btn.setText(btn.getText().toString() + "   " + selectedHour + ":0" + selectedMinute + " AM");
                    } else {
                        btn.setText(btn.getText().toString() + "   " + selectedHour + ":" + selectedMinute + " AM");
                    }
                }
                btn.setTextColor(getResources().getColor(R.color.white));
                //btn.setBackground(getDrawable(R.drawable.button_background_selected)); replaced because required min API 21
                //btn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_background_selected)); replaced because required min API 16
                btn.setBackgroundResource(R.drawable.button_background_selected);
            }
        }, hour, minute, false);//Not 24 hour time
        //mTimePicker.setTitle("Select pickup time");
        mTimePicker.show();
    }

    //function to set date
    public void set(int x) {
        btn = findViewById(x);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH);// current day
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(Main2Activity.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        btn.setText(dayOfMonth + " "
                                + theMonth(monthOfYear) + " " + year);
                    }
                }, mYear, mMonth, mDay);
        setTime();
        datePickerDialog.show();
    }

    public static String theMonth(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }
}
