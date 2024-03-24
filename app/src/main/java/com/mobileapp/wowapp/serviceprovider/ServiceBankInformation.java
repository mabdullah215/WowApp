package com.mobileapp.wowapp.serviceprovider;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

public class ServiceBankInformation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_bank_information);
        TextView tvBankName=findViewById(R.id.tv_bank_name);
        EditText ibanNumber=findViewById(R.id.et_iban_number);
        tvBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] typeList={"National Commercial Bank","Al Rajhi Bank","Samba Financial Group", "Riyad Bank",
                        "Banque Saudi Fransi", "Saudi British Bank", "Arab National Bank",
                        "Alinma Bank", "Alawwal Bank", "Saudi Investment Bank"};
                showList(tvBankName,typeList);
            }
        });
        MaterialButton buttonNext=findViewById(R.id.button_done);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String bankName=tvBankName.getText().toString().trim();
                String iban=ibanNumber.getText().toString().trim();

                if(bankName.equalsIgnoreCase("Select Bank")||iban.isEmpty())
                {
                    showToast("Infomration Missing");
                }
                else
                {
                    ServiceProvider provider=(ServiceProvider) getIntent().getSerializableExtra("item");
                    //provider.setBankName(bankName);
                    //provider.setiBan(iban);
                    updateFirebaseData(provider);
                }
            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(ServiceBankInformation.this);
            }
        });
    }

    public void updateFirebaseData(ServiceProvider provider)
    {
        String uid= FirebaseAuth.getInstance().getUid();
        provider.setId(uid);
        DocumentReference mPostRef= FirebaseFirestore.getInstance().collection("service_providers").document(provider.getId());
        mPostRef.set(provider);
        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
        DataSource source=DataSource.getInstance(this);
        source.setUsertype("");
        Intent i = new Intent(ServiceBankInformation.this, ServiceMainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Animatoo.INSTANCE.animateSlideLeft(ServiceBankInformation.this);
    }


    private void showList(TextView et, String[] list)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select from below");
        builder.setItems(list, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                et.setText(list[which]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}