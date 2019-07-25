package com.whitewebteam.deliverus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PaymentConfirmationActivity extends AppCompatActivity {

    private String phone;
    private EditText confirmationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        phone = getSharedPreferences("userDetailsPref", Context.MODE_PRIVATE).getString("phone", null);;
        confirmationCode = (EditText) findViewById(R.id.confirmation_code);
    }

    public void sendConfirmation(View view) {
        new ProcessHandler(this, "Sending Confirmation...", "sendConfirmation", phone,
                confirmationCode.getText().toString());
    }
}
