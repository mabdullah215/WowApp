package com.mobileapp.wowapp.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.model.Customer;

public class CustomerNewAccount extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_new_account);
        ImageView imgBack=findViewById(R.id.img_back);
        EditText etFullname=findViewById(R.id.et_fullname);
        EditText etEmail=findViewById(R.id.et_email);

        MaterialButton doneButton=findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname=etFullname.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                if(fullname.isEmpty()||email.isEmpty())
                {
                    showToast("Information missing");
                }
                else
                {
                    Customer customer=new Customer();
                    customer.setName(fullname);
                    customer.setEmail(email);

                    startActivity(new Intent(getBaseContext(), BusinessProfile.class).putExtra("item",customer));
                    Animatoo.INSTANCE.animateSlideLeft(CustomerNewAccount.this);
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CustomerNewAccount.this);
            }
        });
    }
}