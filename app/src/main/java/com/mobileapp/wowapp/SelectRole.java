package com.mobileapp.wowapp;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.adapter.RoleListAdapter;

public class SelectRole extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        ImageView imgBack=findViewById(R.id.img_back);
        RecyclerView recyclerView=findViewById(R.id.role_list);
        MaterialButton buttonContinue=findViewById(R.id.button_continue);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        RoleListAdapter adapter=new RoleListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RoleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                adapter.setSelected(position);
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checkDatabase(adapter.getSelected());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(SelectRole.this);
            }
        });
    }

    public void checkDatabase(int position)
    {
        String number=getIntent().getStringExtra("number");
        startActivity(new Intent(getBaseContext(), GeneralProfile.class)
                .putExtra("number",number)
                .putExtra("position",position));
        Animatoo.INSTANCE.animateSlideLeft(SelectRole.this);
    }

}