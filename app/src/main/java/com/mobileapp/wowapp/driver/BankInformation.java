package com.mobileapp.wowapp.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.network.NetworkManager;

public class BankInformation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_information);
        Spinner bankSpinner=findViewById(R.id.spinner_bank);
        NetworkManager manager=NetworkManager.getInstance(this);
        bankSpinner.setAdapter(manager.getBankListAdapter());
        EditText ibanNumber=findViewById(R.id.et_iban_number);
        EditText etAccountName=findViewById(R.id.et_account_name);
        MaterialButton buttonNext=findViewById(R.id.button_done);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int bankid=manager.getBankList().get(bankSpinner.getSelectedItemPosition()).getId();
                String iban=ibanNumber.getText().toString().trim();
                String accountName=etAccountName.getText().toString().trim();
                if(iban.isEmpty()||accountName.isEmpty())
                {
                    showToast("Information Missing");
                }
                else
                {
                    Driver driver=(Driver) getIntent().getSerializableExtra("item");
                    driver.setBankName(String.valueOf(bankid));
                    driver.setAccountName(accountName);
                    driver.setIban(iban);
                    startActivity(new Intent(getBaseContext(), CarInformation.class).putExtra("item",driver));
                    Animatoo.INSTANCE.animateSlideLeft(BankInformation.this);
                }
            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(BankInformation.this);
            }
        });
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