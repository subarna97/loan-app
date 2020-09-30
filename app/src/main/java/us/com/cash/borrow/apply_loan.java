package us.com.cash.borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class apply_loan extends AppCompatActivity {

    Button btnbrowse, btnupload,button;
    EditText bank_account, namebank, bank_ifsc_code, Lon;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;

    FirebaseDatabase database;
    DatabaseReference reff;
    Member member;
    Spinner spinner, spinner2;
    int maxid=0;
    ArrayList<String> spinner_value = new ArrayList<>();
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_loan);

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

        storageReference = FirebaseStorage.getInstance().getReference("Loan");
        databaseReference = FirebaseDatabase.getInstance().getReference("Loan");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);
        bank_account = (EditText)findViewById(R.id.bank_Ac);
        Lon = (EditText)findViewById(R.id.loan_amount);
        namebank = (EditText)findViewById(R.id.bank_name);
        bank_ifsc_code = (EditText)findViewById(R.id.ifsc_code);
        imgview = (ImageView)findViewById(R.id.image_view);

        spinner2 = findViewById(R.id.spinner2);
        progressDialog = new ProgressDialog(apply_loan.this);// context name as per your project name


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });

        button = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        member = new Member();
        reff = database.getInstance().getReference().child("Data ");

        List<String> categories = new ArrayList<>();
        categories.add(0, "choose loan amount $1,000 - 500,000");
        categories.add("$1,000 - 5,000");
        categories.add("$5,000  10,000");
        categories.add("$10,000 - 15,000");
        categories.add("$15,000 - 20,000");
        categories.add("$20,000 - 25,000");
        categories.add("$25,000 - 30,000");
        categories.add("$30,000 - 35,000");
        categories.add("$35,000 - 40,000");
        categories.add("$40,000 - 50,000");
        categories.add("$50,000 - 60,000");
        categories.add("$60,000 - 70,000");
        categories.add("$70,000 - 80,000");
        categories.add("$80,000 - 90,000");
        categories.add("$90,000 - 100,000");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("choose event")) {

                } else {
                    String item = adapterView.getItemAtPosition(i).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String spin = spinner.getSelectedItem().toString();
                if (spinner != null){

                    member.setSpinner(spin);
                    Toast.makeText(apply_loan.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                    //toast.show();

                    reff.child(String.valueOf(maxid+1)).setValue(member);

                }

            }
        });

        set_spinner();
        ArrayAdapter arrayAdapter = new ArrayAdapter(apply_loan.this,android.R.layout.simple_dropdown_item_1line,spinner_value);

        spinner2.setAdapter(arrayAdapter);

    }

    void set_spinner(){
        spinner_value.add("loan tenure");
        spinner_value.add("6 month to 12 months");
        spinner_value.add("12 month to 18 months");
        spinner_value.add("18 month to 24 months");
        spinner_value.add("24 month to 30 months");
        spinner_value.add("30 month to 36 months");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String lone_amount = Lon.getText().toString().trim();
                            String B_Account = bank_account.getText().toString().trim();
                            String bankname = namebank.getText().toString().trim();
                            String ifsccode = bank_ifsc_code.getText().toString().trim();


                            progressDialog.dismiss();

                            if (lone_amount.isEmpty()){
                                Lon.setError("Enter loan Amount");
                                Lon.requestFocus();
                                return;
                            }
                            if (B_Account.isEmpty()){
                                bank_account.setError("Bank Account number is emty");
                                bank_account.requestFocus();
                                return;
                            }

                            if (bankname.isEmpty()){
                                namebank.setError("Bank Account name is emty");
                                namebank.requestFocus();
                                return;
                            }
                            if (ifsccode.isEmpty()){
                                bank_ifsc_code.setError("Ifsc is emty");
                                bank_ifsc_code.requestFocus();
                                return;
                            }


                            Toast.makeText(getApplicationContext(), "Uploaded Successfully ", Toast.LENGTH_SHORT).show();
                            @SuppressWarnings("VisibleForTests")
                            loan imageUploadInfo = new loan(lone_amount,B_Account, bankname,ifsccode, taskSnapshot.getUploadSessionUri().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);

                        }
                    });
        }
        else {

            Toast.makeText(apply_loan.this, "Please Select Image and fill all details", Toast.LENGTH_LONG).show();

        }

    }
}