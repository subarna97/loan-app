package us.com.cash.borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class personal_deatils extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName,father,dob,address, editTextEmail, editTextPassword, phnumber,confirmpassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_deatils);

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-2590445396872568/7861144771");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        phnumber = findViewById(R.id.ph_number);
        confirmpassword = findViewById(R.id.edit_text_confirm_password);
        editTextPassword = findViewById(R.id.edit_text_password);
        father = findViewById(R.id.fathername);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button_register).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String ph = phnumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String confirm_password = confirmpassword.getText().toString().trim();
        final String fathername = father.getText().toString().trim();
        final String dathofbirth = dob.getText().toString().trim();
        final String CurrentAddress = address.getText().toString().trim();


        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (ph.isEmpty()) {
            phnumber.setError("Ph Number required");
            phnumber.requestFocus();
            return;
        }

        if (ph.length() < 7) {
            phnumber.setError("Enter 7 digit Ph Number");
            phnumber.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (password.equals(confirm_password)) {
            Toast.makeText(this, "Waiting...", Toast.LENGTH_SHORT).show();
        }else{
            confirmpassword.setError("Check Confirm password");
            confirmpassword.requestFocus();
            return;
        }

        if (fathername.isEmpty()) {
            father.setError("Father name required");
            father.requestFocus();
            return;
        }

        if (CurrentAddress.isEmpty()) {
            address.setError("Father name required");
            address.requestFocus();
            return;
        }


        if (dathofbirth.isEmpty()) {
            dob.setError("Dath of birth required");
            dob.requestFocus();
            return;
        }







        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    name,
                                    fathername,
                                    dathofbirth,
                                    CurrentAddress,
                                    email,
                                    ph,
                                    confirm_password


                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(personal_deatils.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();

                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(personal_deatils.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                registerUser();
                break;
        }



    }
}