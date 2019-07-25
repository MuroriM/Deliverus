package com.whitewebteam.deliverus;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.Callable;

public class ResetPwdActivity extends AppCompatActivity
{

    private EditText pwd;
    private Button reset;
    boolean pwdBool;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        pwd = (EditText) findViewById(R.id.newPwd);
        reset = (Button) findViewById(R.id.reset);

        setValidation();
    }

    private void setValidation() {
        new TextChangedListener(this).addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return pwdValidation();
            }
        }, pwd);
    }

    @Nullable
    private Boolean pwdValidation()
    {
        if (!pwd.getText().toString().equals(""))
        {
            if (pwd.getText().toString().length() < 5)
            {
                pwd.setError("Password must contain at least 5 characters");
                pwdBool = false;
            }
            else
            {
                pwdBool = true;
            }
        }
        else
        {
            pwdBool = false;
        }
        setResetButton();
        return null;
    }

    private void setResetButton()
    {
        if (pwdBool)
        {
            reset.setEnabled(true);
        }
        else
        {
            reset.setEnabled(false);
        }
    }

    public void resetPwd(View view)
    {
        new ProcessHandler(this, "Resetting password...", "resetPwd", pwd.getText().toString());
    }
}
