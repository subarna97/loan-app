package us.com.cash.borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class wallet extends AppCompatActivity {

    public static final String TAG = "MyTag";
    TextView textViewwallet, textViewDescription;
    Button buttonMoreInfo;

    // Remote Config keys
    private static final String TITLE_KEY = "wallet";
    private static final String DESCRIPTION_KEY = "appname";
    private static final String MORE_INFO_KEY = "send_bank";

    // Firebase Remote Config
    private FirebaseRemoteConfig firebaseRemoteConfig;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        send = findViewById(R.id.send_bank);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(wallet.this,Procecing_charge.class));
            }
        });


        textViewwallet = findViewById(R.id.wallet);
        textViewDescription = findViewById(R.id.appname);
        buttonMoreInfo = findViewById(R.id.send_bank);

        // Get Firebase Remote Config instance.
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Create a Remote Config Setting to enable developer mode,
        FirebaseRemoteConfigSettings.Builder configBuilder = new FirebaseRemoteConfigSettings.Builder();

        // Sets the minimum interval between successive fetch calls.
        /**
         * For developer mode I'm setting 0 (zero) second
         * The default mode is 12 Hours. So for production mode it will be 12 hours
         */
        if (BuildConfig.DEBUG) {
            long cacheInterval = 0;
            configBuilder.setMinimumFetchIntervalInSeconds(cacheInterval);
        }
        // finally build config settings and sets to Remote Config
        firebaseRemoteConfig.setConfigSettingsAsync(configBuilder.build());

        /**
         *  Set default Remote Config parameter values. An app uses the in-app default values, and
         * when you need to adjust those defaults, you set an updated value for only the values you
         * want to change in the Firebase console
         */
        //set default values
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        fetchRemoteTitle();
    }

    private void fetchRemoteTitle() {

        // set text from remote
        textViewwallet.setText(firebaseRemoteConfig.getString(TITLE_KEY));
        textViewDescription.setText(firebaseRemoteConfig.getString(DESCRIPTION_KEY));
        buttonMoreInfo.setText(firebaseRemoteConfig.getString(MORE_INFO_KEY));

        // [START fetch_config_with_callback]
        // override default value from Remote Config
        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(wallet.this, "congratulations",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(wallet.this, "",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END fetch_config_with_callback]



    }
}