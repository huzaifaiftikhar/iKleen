package org.huzaifa.ikleen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.bitmap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ID_SELECTED_GALLERY_INTENT_IMAGE = 1;
    private static final float MAX_IMAGE_SIZE = 288f;
    private EditText inputEmail, inputPassword, inputRetypePassword, inputName, inputNumber, inputAddress, inputAddress2, inputPincode;
    private String email, password, retypepassword, name, number, address, address2, pincode;
    private Button btnSignUp, btnAddPhoto;
    private FirebaseAuth auth;

    private ProgressDialog progressDialog;
    private String encodedImage = "";
    private CircleImageView profilePhoto;

    private String userID;
    UserInformation userInformation;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ikleen_logo3);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        auth = FirebaseAuth.getInstance();
        profilePhoto = findViewById(R.id.pic);
        btnAddPhoto = findViewById(R.id.btn_addPhoto);
        btnAddPhoto.setOnClickListener(this);
        btnSignUp = findViewById(R.id.sign_up_button);
        btnSignUp.setOnClickListener(this);
        inputName = findViewById(R.id.name);
        inputNumber = findViewById(R.id.number);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputRetypePassword = findViewById(R.id.retype_password);
        inputAddress = findViewById(R.id.address);
        inputAddress2 = findViewById(R.id.address2);
        inputPincode = findViewById(R.id.pincode);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addPhoto:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, ID_SELECTED_GALLERY_INTENT_IMAGE);
                break;
            case R.id.sign_up_button:
                signup();
        }
    }

    private void signup() {
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        retypepassword = inputRetypePassword.getText().toString().trim();
        name = inputName.getText().toString().trim();
        number = inputNumber.getText().toString().trim();
        address = inputAddress.getText().toString().trim();
        address2 = inputAddress2.getText().toString().trim();
        pincode = inputPincode.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            inputName.requestFocus();
            inputName.setError("This field is required");
            return;
        }

        if (TextUtils.isEmpty(number)) {
            inputNumber.requestFocus();
            inputNumber.setError("This field is required");
            return;
        }

        if (number.length() != 10) {
            inputNumber.requestFocus();
            inputNumber.setError("Invalid mobile number");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            inputEmail.requestFocus();
            inputEmail.setError("Enter email address");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.requestFocus();
            inputPassword.setError("Create a password");
            return;
        }

        if (password.length() < 6) {
            inputPassword.requestFocus();
            inputPassword.setError("Minimum password length is 6 characters");
            return;
        }

        if (!(password.equals(retypepassword))) {
            inputRetypePassword.requestFocus();
            inputRetypePassword.setError("Passwords do not match");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            inputAddress.requestFocus();
            inputAddress.setError("This field is required");
            return;
        }

        if (TextUtils.isEmpty(pincode)) {
            inputPincode.requestFocus();
            inputPincode.setError("This field is required");
            return;
        }

        if (pincode.length() != 6) {
            inputPincode.requestFocus();
            inputPincode.setError("Invalid Pincode");
            return;
        }
        //To hide the keyboard when Create Account button on app is clicked.
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Creating Account....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                //Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        inputPassword.setError(getString(R.string.error_weak_password));
                        inputPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        inputEmail.setError(getString(R.string.error_invalid_email));
                        inputEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        inputEmail.setError(getString(R.string.error_user_exists));
                        inputEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e("SIGNUP_ACTIVITY", e.getMessage());
                    }

                } else {

                    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edt = pref.edit();
                    edt.putBoolean("activity_executed", true);
                    edt.apply();

                    mAuth = FirebaseAuth.getInstance();
                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    myRef = mFirebaseDatabase.getReference();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        userID = user.getUid();
                    }

                    if (TextUtils.isEmpty(address2)) {
                        userInformation = new UserInformation(email, name, number, address, "", pincode, encodedImage);
                    } else {
                        userInformation = new UserInformation(email, name, number, address, address2, pincode, encodedImage);
                    }
                    myRef.child("users").child(userID).setValue(userInformation);
                    Toast.makeText(SignupActivity.this, "Welcome! Registration completed successfully!", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("ACCOUNT_CREATED", "true");
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ID_SELECTED_GALLERY_INTENT_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Uri imageUri = data.getData();
                profilePhoto.setImageURI(imageUri);
                Bitmap imageBitmap = ((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
                Bitmap scaledBitmap = scaleDown(imageBitmap, MAX_IMAGE_SIZE, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            }
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        if (ratio < 1) {
            int width = Math.round(ratio * realImage.getWidth());
            int height = Math.round(ratio * realImage.getHeight());
            return Bitmap.createScaledBitmap(realImage, width, height, filter);
        } else {
            return realImage;
        }
    }
}

