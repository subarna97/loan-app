package us.com.cash.borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Status_Main extends AppCompatActivity {

    public static final String TAG = "MyTag";
    public static final String NODE_USER = "users3";
    FirebaseAuth mauth;

    Button wallet, receipt, pro_charge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status__main);

        mauth = FirebaseAuth.getInstance();

        wallet = findViewById(R.id.your_walllet);
        receipt = findViewById(R.id.paymentreceipt);
        pro_charge = findViewById(R.id.charge);


        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Status_Main.this, wallet.class));
            }
        });

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Status_Main.this, upload_payment.class));
            }
        });

        pro_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Status_Main.this, Procecing_charge.class));
            }
        });


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
                    Toast.makeText(Status_Main.this, "your application is approved", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}