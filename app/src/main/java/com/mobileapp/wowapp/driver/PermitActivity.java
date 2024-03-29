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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.jsibbold.zoomage.ZoomageView;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.NetworkManager;

public class PermitActivity extends AppCompatActivity {
    ZoomageView imgPermit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit);
        imgPermit=findViewById(R.id.img_permit);
        imgPermit.setImageDrawable(writeTextOnDrawable10());
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
        Compaign compaign=(Compaign) getIntent().getSerializableExtra("campaign");
        NetworkManager manager=NetworkManager.getInstance(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // You can adjust the sample size as needed
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_permit, options);

        // Create a copy of the bitmap to prevent modifying the original
        Bitmap bm = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        //paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(this, 14));
        Canvas canvas = new Canvas(bm);
        Log.i("canvasWidth",""+canvas.getWidth());
        Log.i("canvasHeight",""+canvas.getHeight());
        int xPos = (canvas.getWidth() / 3);
        canvas.drawText(manager.getDriver().getName(), xPos, getYposition(40,canvas), paint);
        canvas.drawText(manager.getDriver().getRegistrationNo(), xPos, getYposition(0,canvas), paint);
        canvas.drawText(manager.getDriver().getCarNo()+","+manager.getDriver().getCarMake()+","+manager.getDriver().getCarModel(), xPos, getYposition(-48,canvas), paint);
        canvas.drawText(compaign.getCustomer_name(), xPos, getYposition(-90,canvas), paint);
        canvas.drawText(Converter.datePreview(compaign.getEnd_datetime()), xPos, getYposition(-135,canvas), paint);


        return new BitmapDrawable(getResources(), bm);
    }

    private BitmapDrawable writeTextOnDrawable9() {
        Compaign compaign = (Compaign) getIntent().getSerializableExtra("campaign");
        NetworkManager manager = NetworkManager.getInstance(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // You can adjust the sample size as needed
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_permit, options);

        // Create a copy of the bitmap to prevent modifying the original
        Bitmap bm = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
       // paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);

        float textSizeDP = 14; // Text size in dp
        float textSizePX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSizeDP, getResources().getDisplayMetrics());
        paint.setTextSize(textSizePX);

        Canvas canvas = new Canvas(bm);
        Log.i("canvasWidth", "" + canvas.getWidth());
        Log.i("canvasHeight", "" + canvas.getHeight());

        int canvasHeight = canvas.getHeight();

        // Calculate Y positions dynamically
        int marginFromTopDP = 40; // Adjust this as needed
        int yPosDriverName = getYposition(marginFromTopDP, canvasHeight/2);
        int yPosRegistrationNo = getYposition(-5, canvasHeight/2);
        int yPosCarDetails = getYposition(-45, canvasHeight/2);
        int yPosCustomerName = getYposition(-90, canvasHeight/2);
        int yPosEndDate = getYposition(-135, canvasHeight/2);

        int xPos = canvas.getWidth() / 3;

        canvas.drawText(manager.getDriver().getName(), xPos, yPosDriverName, paint);
        canvas.drawText(manager.getDriver().getRegistrationNo(), xPos, yPosRegistrationNo, paint);
        canvas.drawText(manager.getDriver().getCarNo() + "," + manager.getDriver().getCarMake() + "," + manager.getDriver().getCarModel(), xPos, yPosCarDetails, paint);
        canvas.drawText(compaign.getCustomer_name(), xPos, yPosCustomerName, paint);
        canvas.drawText(Converter.datePreview(compaign.getEnd_datetime()), xPos, yPosEndDate, paint);

        return new BitmapDrawable(getResources(), bm);
    }


    private BitmapDrawable writeTextOnDrawable10() {
        Compaign compaign = (Compaign) getIntent().getSerializableExtra("campaign");
        NetworkManager manager = NetworkManager.getInstance(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // You can adjust the sample size as needed
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_permit, options);

        // Create a copy of the bitmap to prevent modifying the original
        Bitmap bm = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        //paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);

        float textSizeDP = 12; // Text size in dp
        float textSizePX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSizeDP, getResources().getDisplayMetrics());
        paint.setTextSize(textSizePX);

        Canvas canvas = new Canvas(bm);
        Log.i("canvasWidth", "" + canvas.getWidth());
        Log.i("canvasHeight", "" + canvas.getHeight());

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // Adjust these percentages as needed
        float yPosDriverNamePercent = 0.456f;
        float yPosRegistrationNoPercent = 0.505f;
        float yPosCarDetailsPercent = 0.55f;
        float yPosCustomerNamePercent = 0.595f;
        float yPosEndDatePercent = 0.645f;

        int xPos = canvasWidth / 3;

        // Calculate Y positions based on canvas height and percentages
        int yPosDriverName = (int) (canvasHeight * yPosDriverNamePercent);
        int yPosRegistrationNo = (int) (canvasHeight * yPosRegistrationNoPercent);
        int yPosCarDetails = (int) (canvasHeight * yPosCarDetailsPercent);
        int yPosCustomerName = (int) (canvasHeight * yPosCustomerNamePercent);
        int yPosEndDate = (int) (canvasHeight * yPosEndDatePercent);

        canvas.drawText(manager.getDriver().getName(), xPos, yPosDriverName, paint);
        canvas.drawText(manager.getDriver().getRegistrationNo(), xPos, yPosRegistrationNo, paint);
        canvas.drawText(manager.getDriver().getCarNo() + "," + manager.getDriver().getCarMake() + "," + manager.getDriver().getCarModel(), xPos, yPosCarDetails, paint);
        canvas.drawText(compaign.getCustomer_name(), xPos, yPosCustomerName, paint);
        canvas.drawText(Converter.datePreview(compaign.getEnd_datetime()), xPos, yPosEndDate, paint);

        return new BitmapDrawable(getResources(), bm);
    }


    // Method to calculate Y position dynamically based on margin from top in dp
    private int getYposition(int marginFromTopDP, int canvasHeight) {
        float marginFromTopPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginFromTopDP, getResources().getDisplayMetrics());
        return (int) (canvasHeight - marginFromTopPX);
    }


    private BitmapDrawable writeTextOnDrawable3() {
        Compaign compaign = (Compaign) getIntent().getSerializableExtra("campaign");
        NetworkManager manager = NetworkManager.getInstance(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // You can adjust the sample size as needed
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_permit, options);

        // Create a copy of the bitmap to prevent modifying the original
        Bitmap bm = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(tf);

        float textSizeInDp = 14; // Text size in dp
        float textSizeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp, getResources().getDisplayMetrics());
        paint.setTextSize(textSizeInPx);

        Canvas canvas = new Canvas(bm);
        float canvasWidth = canvas.getWidth();
        float canvasHeight = canvas.getHeight();

        float xPos = canvasWidth / 2; // Center horizontally

        // Calculate Y positions based on canvas height
        float yPos1 = getYposition(40, canvasHeight);
        float yPos2 = getYposition(0, canvasHeight);
        float yPos3 = getYposition(-48, canvasHeight);
        float yPos4 = getYposition(-90, canvasHeight);
        float yPos5 = getYposition(-135, canvasHeight);

        // Draw text on canvas
        canvas.drawText(manager.getDriver().getName(), xPos, yPos1, paint);
        canvas.drawText(manager.getDriver().getRegistrationNo(), xPos, yPos2, paint);
        canvas.drawText(manager.getDriver().getCarNo() + "," + manager.getDriver().getCarMake() + "," + manager.getDriver().getCarModel(), xPos, yPos3, paint);
        canvas.drawText(compaign.getCustomer_name(), xPos, yPos4, paint);
        canvas.drawText(Converter.datePreview(compaign.getEnd_datetime()), xPos, yPos5, paint);

        return new BitmapDrawable(getResources(), bm);
    }

    // Function to calculate Y position based on percentage of canvas height
    private float getYposition(float percentage, float canvasHeight) {
        return canvasHeight * (1 - percentage / 100); // Percentage of canvas height from the bottom
    }

    private BitmapDrawable writeTextOnDrawable2() {
        Compaign compaign = (Compaign) getIntent().getSerializableExtra("campaign");
        NetworkManager manager = NetworkManager.getInstance(this);

        // Load the original bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true; // Ensure the bitmap is mutable
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_permit, options);

        // Resize the bitmap to reduce memory usage
        int desiredWidth = imgPermit.getWidth(); // specify the desired width
        int desiredHeight = imgPermit.getHeight(); // specify the desired height
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);

        // Define paint settings
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(this, 8));

        // Create a bitmap to hold the text
        Bitmap textBitmap = Bitmap.createBitmap(resizedBitmap.getWidth(), resizedBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas textCanvas = new Canvas(textBitmap);

        // Draw the text onto the text canvas
        int xPos = (textCanvas.getWidth() / 3);
        textCanvas.drawText(manager.getDriver().getName(), xPos, getYposition(85, textCanvas), paint);
        textCanvas.drawText(manager.getDriver().getRegistrationNo(), xPos, getYposition(-5, textCanvas), paint);
        textCanvas.drawText(manager.getDriver().getCarNo() + "," + manager.getDriver().getCarMake() + "," + manager.getDriver().getCarModel(), xPos, getYposition(-95, textCanvas), paint);
        textCanvas.drawText(compaign.getCustomer_name(), xPos, getYposition(-185, textCanvas), paint);
        textCanvas.drawText(Converter.datePreview(compaign.getEnd_datetime()), xPos, getYposition(-280, textCanvas), paint);

        // Combine the resized bitmap and the text bitmap
        Canvas combinedCanvas = new Canvas(resizedBitmap);
        combinedCanvas.drawBitmap(textBitmap, 0, 0, null);

        // Return a BitmapDrawable
        return new BitmapDrawable(getResources(), resizedBitmap);
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