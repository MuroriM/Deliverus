package com.whitewebteam.deliverus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.Callable;

public class ForgotPwdActivity extends AppCompatActivity {

    static EditText phone, securityA;
    private Button proceed;
    static TextView securityQTextView, securityQ;
    static Button submit;
    private boolean phoneBool, securityABool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        phone = (EditText) findViewById(R.id.phoneFP);
        proceed = (Button) findViewById(R.id.proceedFP);
        securityQTextView = (TextView) findViewById(R.id.securityQTextViewFP);
        securityQ = (TextView) findViewById(R.id.securityQ);
        securityA = (EditText) findViewById(R.id.securityAFP);
        submit = (Button) findViewById(R.id.submit);

        setValidations();
    }

    public void setValidations() {
        TextChangedListener textChangedListener = new TextChangedListener(this);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return phoneValidation();
            }
        }, phone);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return securityAValidation();
            }
        }, securityA);
    }

    public Boolean phoneValidation() {
        phoneBool = !phone.getText().toString().equals("");
        setProceedButton();
        return null;
    }

    public Boolean securityAValidation() {
        securityABool = !securityA.getText().toString().equals("");
        setSubmitButton();
        return null;
    }

    public void setProceedButton() {
        if (phoneBool) {
            proceed.setEnabled(true);
        } else {
            proceed.setEnabled(false);
        }
    }

    public void proceedFP(View view) {
        new ProcessHandler(this, "Retrieving security question...", "forgotPwd",
                phone.getText().toString());
    }

    public void setSubmitButton() {
        if (securityABool) {
            submit.setEnabled(true);
        } else {
            submit.setEnabled(false);
        }
    }

    public void submit(View view) {
        new ProcessHandler(this, "Processing...", "securityQ", securityA.getText().toString());
    }
}
