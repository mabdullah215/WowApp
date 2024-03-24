package com.mobileapp.wowapp.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.model.Driver;

import java.util.Locale;

public class CarInformation extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_information);
        MaterialButton buttonNext=findViewById(R.id.button_done);
        Spinner spinnercarMake=findViewById(R.id.spinner_car_make);
        EditText etCarModel=findViewById(R.id.et_car_model);
        Spinner spinnercarColor=findViewById(R.id.spinner_car_color);
        Spinner spinnercarYear=findViewById(R.id.spinner_car_year);
        EditText etCharacterPlate=findViewById(R.id.et_character_plate);
        EditText etNumPlate=findViewById(R.id.et_number_plate);
        String [] makes={"Toyota","Hyundai","Bentley","Ford","Honda","Kia"};
        String [] colors={"White","Black","Blue","Green","Grey","Silver","Red"};
        String [] yearList={"2023","2022","2021","2020","2019","2018","2017","2016","2015"};
        spinnercarMake.setAdapter(getArrayAdapterforlist(makes));
        spinnercarColor.setAdapter(getArrayAdapterforlist(colors));
        spinnercarYear.setAdapter(getArrayAdapterforlist(yearList));



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String carMake=spinnercarMake.getSelectedItem().toString().trim();
                String carModel=etCarModel.getText().toString().trim();
                String year=spinnercarYear.getSelectedItem().toString().trim();
                String color=spinnercarColor.getSelectedItem().toString().trim();
                String carNo=etNumPlate.getText().toString().trim()+"-"+etCharacterPlate.getText().toString().trim();

                if(carModel.isEmpty())
                {
                    showToast("Please enter car model information");
                }
                else
                {
                    Driver driver=(Driver) getIntent().getSerializableExtra("item");
                    driver.setCarMake(carMake);
                    driver.setCarModel(carModel);
                    driver.setCarYear(year);
                    driver.setCarColor(color);
                    driver.setCarNo(carNo);
                    startActivity(new Intent(getBaseContext(), DocumentsInformation.class).putExtra("item",driver));
                    Animatoo.INSTANCE.animateSlideLeft(CarInformation.this);
                }
            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CarInformation.this);
            }
        });
    }

    public ArrayAdapter getArrayAdapterforlist(String []list)
    {
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
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