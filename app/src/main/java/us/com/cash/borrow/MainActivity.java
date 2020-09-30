package us.com.cash.borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";
    public static final String NODE_USER = "users2";
    FirebaseAuth mauth;

    Button personal, Upload_Addhar, uploadpan, apply_loan, status,photoupload, about_us;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-2590445396872568/7861144771");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2590445396872568/2608818099");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        personal = (Button) findViewById(R.id.personal);
        Upload_Addhar = (Button) findViewById(R.id.upload_Addhar);
        uploadpan = (Button) findViewById(R.id.uploadpan);
        apply_loan = (Button) findViewById(R.id.applyloan);
        status = (Button) findViewById(R.id.status);
        photoupload = (Button) findViewById(R.id.uploadphoto);
        about_us = (Button) findViewById(R.id.about_us);


        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,about_us.class));
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,about_us.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });

            }
        });



        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,personal_deatils.class));
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,personal_deatils.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
            }
        });



        Upload_Addhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,upload_addhar_voter.class));
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,upload_addhar_voter.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
            }
        });



        uploadpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,Upload_PanCard.class));
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,Upload_PanCard.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
            }
        });



        photoupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,Upload_your_photo.class));
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,Upload_your_photo.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });

            }
        });



        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });
            }
        });





        apply_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(MainActivity.this,apply_loan.class));
                }

                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this,apply_loan.class));
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }

                });

            }
        });



        mauth = FirebaseAuth.getInstance();


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            savetoken(token);
                        }else{


                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mauth.getCurrentUser() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void savetoken(String token){
        String email = mauth.getCurrentUser().getEmail();

        user2 user = new user2(email, token);

        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference(NODE_USER);
        dbuser.child(mauth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "please submit all details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}