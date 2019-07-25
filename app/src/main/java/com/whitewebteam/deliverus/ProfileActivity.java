package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.Callable;

public class ProfileActivity extends AppCompatActivity {

    private EditText fnameEditText, lnameEditText, phoneEditText, emailEditText;
    private String fname, lname, phone, email;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");*/

       // ImageButton imageButton=( ImageButton)findViewById(R.id.profileBack);

        TextView name = (TextView) findViewById(R.id.user_profile_name);
        fnameEditText = (EditText) findViewById(R.id.fname);
        lnameEditText = (EditText) findViewById(R.id.lname);
        phoneEditText = (EditText) findViewById(R.id.phone);
        emailEditText = (EditText) findViewById(R.id.email);
        confirm = (Button) findViewById(R.id.confirm_change_details);

        SharedPreferences userDetailsPref = getSharedPreferences("userDetailsPref",
                Context.MODE_PRIVATE);
        fname = userDetailsPref.getString("fname", null);
        lname = userDetailsPref.getString("lname", null);
        phone = userDetailsPref.getString("phone", null);
        email = userDetailsPref.getString("email", null);

        name.setText(fname + " " + lname);
        fnameEditText.setText(fname);
        lnameEditText.setText(lname);
        phoneEditText.setText(phone);
        emailEditText.setText(email);

        TextView[] textViews = {(TextView) findViewById(R.id.edit_fname),
                (TextView) findViewById(R.id.edit_lname), (TextView) findViewById(R.id.edit_phone),
                (TextView) findViewById(R.id.edit_email)};
        EditText[] editTexts = {fnameEditText, lnameEditText, phoneEditText, emailEditText};

        setEditable(textViews, editTexts);

        TextChangedListener textChangedListener = new TextChangedListener(this);
        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return fnameListener();
            }
        }, fnameEditText);
        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return lnameListener();
            }
        }, lnameEditText);
        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return phoneListener();
            }
        }, phoneEditText);
        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return emailListener();
            }
        }, emailEditText);
    }

    private void setEditable(TextView[] textViews, final EditText[] editTexts) {
        for (int i = 0; i < textViews.length; i++) {
            final int finalI = i;
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTexts[finalI].setEnabled(true);
                }
            });
        }
    }

    @Nullable
    private Boolean fnameListener() {
        if (fnameEditText.getText().toString().equals(fname)) {
            confirm.setEnabled(false);
        } else {
            confirm.setEnabled(true);
        }
        return null;
    }

    @Nullable
    private Boolean lnameListener() {
        if (lnameEditText.getText().toString().equals(lname)) {
            confirm.setEnabled(false);
        } else {
            confirm.setEnabled(true);
        }
        return null;
    }

    @Nullable
    private Boolean phoneListener() {
        if (phoneEditText.getText().toString().equals(phone)) {
            confirm.setEnabled(false);
        } else {
            confirm.setEnabled(true);
        }
        return null;
    }

    @Nullable
    private Boolean emailListener() {
        if (emailEditText.getText().toString().equals(email)) {
            confirm.setEnabled(false);
        } else {
            confirm.setEnabled(true);
        }
        return null;
    }

    public void updateUserDetails(View view) {
        new ProcessHandler(this, "Updating your details...", "updateUserDetails",
                String.valueOf(getSharedPreferences("userDetailsPref", Context.MODE_PRIVATE)
                        .getInt("id", 0)), fnameEditText.getText().toString(),
                lnameEditText.getText().toString(), emailEditText.getText().toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right_back, R.anim.right_to_left_back);
    }

    public void goBack()
    {
        Intent prflBack = new Intent(this,MainActivity.class);
        startActivity(prflBack);
    }
}
