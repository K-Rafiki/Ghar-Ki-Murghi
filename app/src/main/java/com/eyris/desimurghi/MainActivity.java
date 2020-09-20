package com.eyris.desimurghi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eyris.desimurghi.Onboarding.DialogOnboarding;
import com.eyris.desimurghi.Onboarding.OnboardingScreen;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private double lat;
    private double lon;
    private String address;
    private EditText et_address;
    private ImageView iv_logo;
    private Button bt_checkout;

    private RadioGroup rg_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        rg_payment.check(R.id.rb_cod);
        Picasso.get().load(R.mipmap.mlauncher).fit().into(iv_logo);
        bt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("You checked out !");
//                Toast.makeText(MainActivity.this, "You checked out !", Toast.LENGTH_SHORT).show();
            }
        });
//        iv_logo.setimage(R.mipmap.mlauncher);

        address = getIntent().getStringExtra("addr");
        lat = getIntent().getDoubleExtra("lat", 0.0);
        lon = getIntent().getDoubleExtra("lon", 0.0);

        et_address.setText(address);

        //TODO If first time
        boolean firsttime = getSharedPreferences("internal", MODE_PRIVATE).getBoolean("firsttime", true);
        if (firsttime) {
            getSharedPreferences("internal", MODE_PRIVATE).edit().putBoolean("firsttime", false).apply();
            (new DialogOnboarding()).show(getSupportFragmentManager(), "onboard");
        }

    }

    private void showToast(String text) {
        View view = getLayoutInflater().inflate(R.layout.custom_toast, null);
        TextView toastTextView = view.findViewById(R.id.tv_text);
        toastTextView.setText(text);

        AssetManager am = getAssets();
        toastTextView.setTypeface(Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "Arial.ttf")), Typeface.BOLD);

        Toast mToast = new Toast(MainActivity.this);
        mToast.setView(view);
        mToast.setGravity(Gravity.BOTTOM, 0, 30);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    private void findViews() {
        iv_logo = findViewById(R.id.iv_logo);
        rg_payment = findViewById(R.id.rg_payment);
        et_address = findViewById(R.id.et_address);
        bt_checkout = findViewById(R.id.bt_checkout);
    }


}