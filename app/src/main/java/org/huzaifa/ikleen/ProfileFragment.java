package org.huzaifa.ikleen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView name, number, email, address, address2, pincode, city;
    private CircleImageView profilePhoto;
    private ImageView anim;
    private Button btnSignout;

    private static final String TAG = "ProfileFragment";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    String userID;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        name = v.findViewById(R.id.name);
        number = v.findViewById(R.id.number);
        email = v.findViewById(R.id.email);
        profilePhoto = v.findViewById(R.id.pic);
        anim = v.findViewById(R.id.pic2);
        address = v.findViewById(R.id.address);
        address2 = v.findViewById(R.id.address2);
        pincode = v.findViewById(R.id.pincode);
        //city = v.findViewById(R.id.city);
        btnSignout = v.findViewById(R.id.btn_signout);
        btnSignout.setOnClickListener(this);

        /*city.setVisibility(View.GONE);*/

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("Successfully signed out.");
                }
            }
        };
        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ikleen-1463a.firebaseio.com/users/" + userID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                UserInformation uInfo = dataSnapshot.getValue(UserInformation.class);
                Log.d("Profile Fragment", dataSnapshot.getValue().toString());

                //showData(dataSnapshot);
                name.setText(uInfo.getUserName());
                number.setText(uInfo.getUserNumber());
                email.setText(uInfo.getUserEmail());
                address.setText(uInfo.getUserAddress());
                if (uInfo.getUserAddress2().equals("")) {
                    address2.setVisibility(View.GONE);
                } else {
                    address2.setText(uInfo.getUserAddress2());
                }
                pincode.setText("Delhi - " + uInfo.getUserPincode());

                if (uInfo.getUserImageURI().equals("")) {
                    Log.d(TAG, "uInfo.getUserImageURI" + uInfo.getUserImageURI());
                    if (getContext() != null) {
                        Log.d(TAG, "getContext not null");
                        Glide.with(getContext())
                                .load(R.drawable.default_user_icon_profile)
                                .asBitmap()
                                .dontAnimate()
                                .into(profilePhoto);
                        anim.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Log.d(TAG, "In else part");
                    byte[] decodedByteArray = android.util.Base64.decode(uInfo.getUserImageURI(), Base64.DEFAULT);
                    if (getContext() != null) {
                        Log.d(TAG, "In inside else");
                        Glide.with(getContext())
                                .load(decodedByteArray)
                                .asBitmap()
                                .dontAnimate()
                                .into(profilePhoto);
                        anim.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        /*Map<String, Object> nickname = new HashMap<String, Object>();
        nickname.put("userName", "Alan The Machine");
        myRef.updateChildren(nickname);*/

        return v;
    }

    /*private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            UserInformation uInfo = new UserInformation();
            uInfo.setUserName(ds.child(userID).getValue(UserInformation.class).getUserName()); //set the name
            uInfo.setUserEmail(ds.child(userID).getValue(UserInformation.class).getUserEmail()); //set the email
            uInfo.setUserNumber(ds.child(userID).getValue(UserInformation.class).getUserNumber()); //set the phone_num
            uInfo.setUserAddress(ds.child(userID).getValue(UserInformation.class).getUserAddress()); //set the address
            uInfo.setUserAddress2(ds.child(userID).getValue(UserInformation.class).getUserAddress2()); //set the address2
            uInfo.setUserPincode(ds.child(userID).getValue(UserInformation.class).getUserPincode()); //set the pincode
            uInfo.setUserImageURI(ds.child(userID).getValue(UserInformation.class).getUserImageURI());

            //display all the information
            Log.d(TAG, "showData: name: " + uInfo.getUserName());
            Log.d(TAG, "showData: email: " + uInfo.getUserEmail());
            Log.d(TAG, "showData: phone_num: " + uInfo.getUserNumber());

            name.setText(uInfo.getUserName());
            number.setText(uInfo.getUserNumber());
            email.setText(uInfo.getUserEmail());

            if(uInfo.getUserImageURI().equals("")){
                Log.d(TAG, "uInfo.getUserImageURI" + uInfo.getUserImageURI());
                if (getContext() != null) {
                    Log.d(TAG, "getContext not null");
                    Glide.with(getContext())
                            .load(R.drawable.default_user_icon_profile)
                            .asBitmap()
                            .dontAnimate()
                            .into(profilePhoto);
                    anim.setVisibility(View.INVISIBLE);
                }
            } else {
                Log.d(TAG, "In else part");
                byte[] decodedByteArray = android.util.Base64.decode(uInfo.getUserImageURI(), Base64.DEFAULT);
                if (getContext() != null) {
                    Log.d(TAG, "In inside else");
                    Glide.with(getContext())
                            .load(decodedByteArray)
                            .asBitmap()
                            .dontAnimate()
                            .into(profilePhoto);
                    anim.setVisibility(View.INVISIBLE);
                }
            }

            //address.setText(uInfo.getUserAddress());
            //address2.setText(uInfo.getUserAddress2());

            //if(uInfo.getUserAddress2().equals("")){
              //  address2.setVisibility(View.GONE);
            //}
            //city.setVisibility(View.VISIBLE);
            //pincode.setText(uInfo.getUserPincode());
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signout:
                FirebaseAuth.getInstance().signOut();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("activity_executed", false);
                edt.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
        }
    }
}