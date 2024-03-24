package com.mobileapp.wowapp.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobileapp.wowapp.R;


public class HelpSheet extends BottomSheetDialog
{
    Context mContext;

    public HelpSheet(@NonNull Context context, int theme) {
        super(context, theme);
        mContext=context;
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_help_sheet, null);
        ImageView imgClose=sheetView.findViewById(R.id.img_close);
        LinearLayout layoutWhatsapp=sheetView.findViewById(R.id.layout_whatsapp);
        LinearLayout layoutEmail=sheetView.findViewById(R.id.layout_email);
        LinearLayout layoutCall=sheetView.findViewById(R.id.layout_call);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        layoutWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*Uri uri = Uri.parse("smsto:+923335532538");
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                mContext.startActivity(Intent.createChooser(i, ""));*/

                mContext.startActivity
                        (
                        new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s","966547837900",""))
                        )
                );
            }
        });

        layoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+6123456785"));
                mContext.startActivity(intent);
            }
        });

        layoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "support@wowapp.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I neeed a help");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "this is help required from a client");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                mContext.startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

            }
        });

        getBehavior().setDraggable(false);
        setContentView(sheetView);
    }


}
