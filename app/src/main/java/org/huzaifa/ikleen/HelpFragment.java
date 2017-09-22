package org.huzaifa.ikleen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.huzaifa.ikleen.bottommenu.BottomMenu;
import org.huzaifa.ikleen.pricesmenu.PricesActivity;

import java.util.zip.Inflater;

public class HelpFragment extends Fragment implements View.OnClickListener {

    Button btnFAQs, btnCall, btnLocate, btnCall2, btnLocate2;
    TextView txtEmailUs;

    View rootLayout, cardFace, cardBack;
    View rootLayout2, cardFace2, cardBack2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);

        btnFAQs = v.findViewById(R.id.btn_faqs);
        btnFAQs.setOnClickListener(this);

        btnCall = v.findViewById(R.id.btn_call);
        btnCall.setOnClickListener(this);
        btnLocate = v.findViewById(R.id.btn_locate);
        btnLocate.setOnClickListener(this);

        btnCall2 = v.findViewById(R.id.btn_call2);
        btnCall2.setOnClickListener(this);
        btnLocate2 = v.findViewById(R.id.btn_locate2);
        btnLocate2.setOnClickListener(this);

        txtEmailUs = v.findViewById(R.id.textview_email_us);
        txtEmailUs.setOnClickListener(this);

        rootLayout = v.findViewById(R.id.root_layout);
        cardFace = v.findViewById(R.id.card_front);
        cardFace.setOnClickListener(this);
        cardBack = v.findViewById(R.id.card_back);
        cardBack.setOnClickListener(this);

        rootLayout2 = v.findViewById(R.id.root_layout2);
        cardFace2 = v.findViewById(R.id.card_front2);
        cardFace2.setOnClickListener(this);
        cardBack2 = v.findViewById(R.id.card_back2);
        cardBack2.setOnClickListener(this);

        FlipCode.cardFace = cardFace;
        FlipCode.cardBack = cardBack;
        FlipCode.rootLayout = rootLayout;
        FlipCode.cardFace2 = cardFace2;
        FlipCode.cardBack2 = cardBack2;
        FlipCode.rootLayout2 = rootLayout2;

        FlipCode.card1_flipped = FlipCode.card2_flipped = false;

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_faqs:
                Intent faqs = new Intent(getActivity(), FAQsActivity.class);
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
                Intent callIntent = new Intent(Intent.ACTION_CALL);
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
                Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                callIntent2.setData(Uri.parse("tel:+919651638895"));
                startActivity(callIntent2);
                break;
            case R.id.btn_locate2:
                //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                String map2 = "https://www.google.co.in/maps/place/28%C2%B033'38.0%22N+77%C2%B017'14.6%22E/@28.560561,77.2868358,19z/data=!3m1!4b1!4m5!3m4!1s0x0:0x0!8m2!3d28.560561!4d77.287383";
                Intent locateIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(map2));
                startActivity(locateIntent2);
                break;
            case R.id.card_front:
                FlipCode.card1_flipped = true;
                onCardClick(rootLayout);
                break;
            case R.id.card_back:
                FlipCode.card1_flipped = false;
                onCardClick(rootLayout);
                break;
            case R.id.card_front2:
                FlipCode.card2_flipped = true;
                onCardClick2(rootLayout2);
                break;
            case R.id.card_back2:
                FlipCode.card2_flipped = false;
                onCardClick2(rootLayout2);
                break;
        }
    }

    public void onCardClick(View view) {
        flipCard();
    }

    @Override
    public void onDetach() {
        FlipCode.card1_flipped = false;
        FlipCode.card2_flipped = false;
        super.onDetach();
    }

    private void flipCard() {
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