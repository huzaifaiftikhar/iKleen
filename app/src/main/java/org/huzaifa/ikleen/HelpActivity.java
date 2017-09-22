package org.huzaifa.ikleen;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.huzaifa.ikleen.bottommenu.BottomMenu;
import org.huzaifa.ikleen.pricesmenu.PricesActivity;

import java.util.Locale;

import static android.R.id.message;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    BottomMenu bottomMenu;
    Button btnFAQs, btnCall, btnLocate, btnCall2, btnLocate2;
    TextView txtEmailUs;

    View rootLayout, cardFace, cardBack;
    View rootLayout2, cardFace2, cardBack2;

    @Override
    public void onResume() {
        super.onResume();
        bottomMenu = findViewById(R.id.bottom_menu);
        bottomMenu.select(4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        bottomMenu = findViewById(R.id.bottom_menu);
        bottomMenu.select(4);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ikleen_logo3);

        btnFAQs = findViewById(R.id.btn_faqs);
        btnFAQs.setOnClickListener(this);

        btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(this);
        btnLocate = findViewById(R.id.btn_locate);
        btnLocate.setOnClickListener(this);

        btnCall2 = findViewById(R.id.btn_call2);
        btnCall2.setOnClickListener(this);
        btnLocate2 = findViewById(R.id.btn_locate2);
        btnLocate2.setOnClickListener(this);

        txtEmailUs = findViewById(R.id.textview_email_us);
        txtEmailUs.setOnClickListener(this);

        rootLayout = findViewById(R.id.root_layout);
        cardFace = findViewById(R.id.card_front);
        cardBack = findViewById(R.id.card_back);

        rootLayout2 = findViewById(R.id.root_layout2);
        cardFace2 = findViewById(R.id.card_front2);
        cardBack2 = findViewById(R.id.card_back2);

        bottomMenu.setOnItemClickListener(new BottomMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent home = new Intent(HelpActivity.this, MainActivity.class);
                        startActivity(home);
                        finish();
                        break;
                    case 1:
                        Intent pickup = new Intent(HelpActivity.this, Main2Activity.class);
                        startActivity(pickup);
                        break;
                    case 2:
                        Intent profile = new Intent(HelpActivity.this, LoginActivity.class);
                        startActivity(profile);
                        finish();
                        break;
                    case 3:
                        Intent prices = new Intent(HelpActivity.this, PricesActivity.class);
                        startActivity(prices);
                        finish();
                        break;
                    case 4:
                        //viewFlipper.setDisplayedChild(2);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (cardFace.getVisibility() == View.GONE) {
            flipCard();
        } else if (cardFace2.getVisibility() == View.GONE) {
            flipCard2();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_faqs:
                Intent faqs = new Intent(HelpActivity.this, FAQsActivity.class);
                startActivity(faqs);
                break;
            case R.id.textview_email_us:
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "ikleensvcs@gmail.com", null));
                //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                //intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                break;
            case R.id.btn_call:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+918800174336"));
                startActivity(callIntent);
                break;
            case R.id.btn_locate:
                //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                String map = "https://www.google.co.in/maps/place/iKleen+Services/@28.56524,77.2861213,17z/data=!3m1!4b1!4m5!3m4!1s0x390ce475b956b6d3:0xb2f420771e94f890!8m2!3d28.56524!4d77.28831";
                Intent locateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(locateIntent);
                break;
            case R.id.btn_call2:
                Intent callIntent2 = new Intent(Intent.ACTION_DIAL);
                callIntent2.setData(Uri.parse("tel:+919651638895"));
                startActivity(callIntent2);
                break;
            case R.id.btn_locate2:
                //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                String map2 = "https://www.google.co.in/maps/place/28%C2%B033'38.0%22N+77%C2%B017'14.6%22E/@28.560561,77.2868358,19z/data=!3m1!4b1!4m5!3m4!1s0x0:0x0!8m2!3d28.560561!4d77.287383";
                Intent locateIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(map2));
                startActivity(locateIntent2);
        }
    }

    public void onCardClick(View view) {
        flipCard();
    }

    private void flipCard() {
        /*View rootLayout = findViewById(R.id.card_front);
        View cardFace = findViewById(R.id.card_front);
        View cardBack = findViewById(R.id.card_back);*/

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public void onCardClick2(View view) {
        flipCard2();
    }

    private void flipCard2() {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace2, cardBack2);

        if (cardFace2.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout2.startAnimation(flipAnimation);
    }
}