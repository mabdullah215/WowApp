package com.mobileapp.wowapp.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.model.Compaign;

import java.util.ArrayList;
import java.util.List;

public class CompaignList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compaign_list);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Comapign History");
        RecyclerView recyclerView=findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Compaign> list=new ArrayList<>();
        TextView tvEmpty=findViewById(R.id.tv_empty);
        CompaignListAdapter adapter=new CompaignListAdapter(this,list);
        recyclerView.setAdapter(adapter);
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CompaignList.this);
            }
        });

    }
}