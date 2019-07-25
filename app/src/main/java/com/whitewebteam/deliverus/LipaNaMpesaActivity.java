package com.whitewebteam.deliverus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LipaNaMpesaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipa_na_mpesa);
    }

    public void launchPaymentConfirmation(View view) {
        startActivity(new Intent(this, PaymentConfirmationActivity.class));
        finish();
    }
}
