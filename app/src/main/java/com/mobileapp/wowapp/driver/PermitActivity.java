package com.mobileapp.wowapp.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.jsibbold.zoomage.ZoomageView;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.model.AssignedCampaign;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.NetworkManager;

public class PermitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit);
        ZoomageView imgPermit=findViewById(R.id.img_permit);
        imgPermit.setImageDrawable(writeTextOnDrawable());
        ImageView imgBack=findViewById(R.id.img_back);
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Permit");
        TextView shareButton=findViewById(R.id.button_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Drawable mDrawable = imgPermit.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                Uri uri = Uri.parse(path);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Image"));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(PermitActivity.this);
            }
        });
    }

    private BitmapDrawable writeTextOnDrawable()
    {
        AssignedCampaign assignedCampaign=(AssignedCampaign) getIntent().getSerializableExtra("campaign");
        NetworkManager manager=NetworkManager.getInstance(this);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_permit).copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        //paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(this, 25));
        Canvas canvas = new Canvas(bm);
        int xPos = (canvas.getWidth() / 3);
        canvas.drawText(manager.getDriver().getName(), xPos, getYposition(85,canvas), paint);
        canvas.drawText(manager.getDriver().getRegistrationNo(), xPos, getYposition(-5,canvas), paint);
        canvas.drawText(manager.getDriver().getCarNo()+","+manager.getDriver().getCarMake()+","+manager.getDriver().getCarModel(), xPos, getYposition(-95,canvas), paint);
        canvas.drawText(assignedCampaign.getCustomer_name(), xPos, getYposition(-185,canvas), paint);
        canvas.drawText(Converter.datePreview(assignedCampaign.getEnd_datetime()), xPos, getYposition(-280,canvas), paint);

        return new BitmapDrawable(getResources(), bm);
    }

    public int getYposition(int diff,Canvas canvas)
    {
        int difference=convertToPixels(this,diff);
        int yPos = (int) ((canvas.getHeight() / 2)-difference);
        return yPos;
    }


    public static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }
}