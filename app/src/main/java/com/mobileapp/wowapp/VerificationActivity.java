package com.mobileapp.wowapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mobileapp.wowapp.customer.MainActivity;
import com.mobileapp.wowapp.database.DataSource;
import com.mobileapp.wowapp.driver.DriverHome;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.ServiceMainActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends BaseActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    String code="";
    TextView tvTimer;
    CountDownTimer cTimer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ImageView imgBack=findViewById(R.id.img_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(VerificationActivity.this);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        String number=getIntent().getStringExtra("number");
        PinView etPinView=findViewById(R.id.et_pin);
        tvTimer=findViewById(R.id.tv_timer);
        startTimer();
        ImageView imgProceed=findViewById(R.id.img_proceed);
        imgProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=etPinView.getText().toString().trim();
                if(code!=null)
                {
                    verifyCode(code);
                }
            }
        });

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String title=tvTimer.getText().toString();
                if(title.equalsIgnoreCase("Resend SMS NOW"))
                {
                    AsyncTask.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            sendVerificationCode(number);
                        }
                    });
                }
            }
        });


        AsyncTask.execute(new Runnable()
        {
            @Override
            public void run()
            {
                sendVerificationCode(number);
            }
        });


    }

    private void verifyCode(String code)
    {
        showLoading();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void sendVerificationCode(String number)
    {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            profileSetup();
                        }
                        else
                        {
                            hideLoading();
                            Log.i("errorMessage",task.getException().getMessage());
                            Toast.makeText(VerificationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void profileSetup()
    {
        NetworkManager manager=NetworkManager.getInstance(this);
        manager.sendRequest(APIList.GET_PROFILE, new HashMap<>(), new IResult() {
            @Override
            public void notifyResult(String result, APIResult apiResult)
            {
                hideLoading();
                /*if(apiResult.getStatusCode().equalsIgnoreCase("Ok"))
                {
                    DataSource source=DataSource.getInstance(getBaseContext());
                    source.setUsertype(apiResult.getUserTypeId());
                    source.setUserId(apiResult.getUserId());
                    source.setUserToken(apiResult.getToken());

                    if(apiResult.getUserTypeId()==3)
                    {
                        Intent i = new Intent(getBaseContext(), DriverHome.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Animatoo.INSTANCE.animateSlideLeft(VerificationActivity.this);
                    }
                    else if(apiResult.getUserTypeId()==2)
                    {
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Animatoo.INSTANCE.animateSlideLeft(VerificationActivity.this);
                    }
                    else if(apiResult.getUserTypeId()==4)
                    {
                        Intent i = new Intent(getBaseContext(), ServiceMainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Animatoo.INSTANCE.animateSlideLeft(VerificationActivity.this);
                    }
                }

                else
                {
                    String number=getIntent().getStringExtra("number");
                    finish();
                    startActivity(new Intent(getBaseContext(), SelectRole.class).putExtra("number",number));
                    Animatoo.INSTANCE.animateSlideLeft(VerificationActivity.this);
                }*/
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
        {
            code = phoneAuthCredential.getSmsCode();
        }

        @Override
        public void onVerificationFailed(FirebaseException e)
        {
            Log.i("errorMessage",e.getMessage());
            Toast.makeText(VerificationActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    void startTimer() {
        DecimalFormat df = new DecimalFormat("00");
        cTimer = new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished)
            {
                String seconds= df.format(millisUntilFinished/1000);
                tvTimer.setText("Resend SMS: 00:" + seconds);
            }
            public void onFinish()
            {
                tvTimer.setText("Resend SMS NOW");
            }
        };
        cTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelTimer();
    }

    void cancelTimer()
    {
        if(cTimer!=null)
            cTimer.cancel();
    }

}