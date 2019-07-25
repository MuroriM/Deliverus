package com.whitewebteam.deliverus;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Callable;

public class AnimationSignInSignUpActivity extends AppCompatActivity {

    private TextView tvSignUpInvoker, tvSignInInvoker, pwdSignUpReq;
    private LinearLayout layoutSignIn, layoutSignUp;
    private Button btnSignIn, btnSignUp;
    private TextInputEditText phoneSignIn, pwdSignIn;
    static  TextInputEditText fname, lname, phoneSignUp, email, pwdSignUp;
    private boolean isSignInScreen = true;
    private boolean phoneSignInBool, pwdSignInBool, fnameBool, lnameBool, phoneSignUpBool,
            phoneSignUpBool1, pwdSignUpBool, pwdSignUpBool1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_sign_in_sign_up);

        /*View authentication= getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        authentication.setSystemUiVisibility(uiOptions);*/

        Typeface customFont = Typeface.createFromAsset(getAssets(),  "fonts/quicksand_regular.ttf");

        tvSignUpInvoker = (TextView) findViewById(R.id.tv_sign_up_invoker);
        tvSignInInvoker = (TextView) findViewById(R.id.tv_sign_in_invoker);

        layoutSignIn = (LinearLayout) findViewById(R.id.sign_in_layout);
        layoutSignUp = (LinearLayout) findViewById(R.id.sign_up_layout);

        btnSignIn = (Button) findViewById(R.id.sign_in_btn);
        btnSignIn.setTypeface(customFont);
        btnSignUp = (Button) findViewById(R.id.sign_up_btn);
        btnSignUp.setTypeface(customFont);

        phoneSignIn = (TextInputEditText) findViewById(R.id.phone_sign_in);
        phoneSignIn.setTypeface(customFont);
        pwdSignIn = (TextInputEditText) findViewById(R.id.pwd_sign_in);
        pwdSignIn.setTypeface(customFont);

        fname = (TextInputEditText) findViewById(R.id.fname_sign_up);
        fname.setTypeface(customFont);
        lname = (TextInputEditText) findViewById(R.id.lname_sign_up);
        lname.setTypeface(customFont);
        phoneSignUp = (TextInputEditText) findViewById(R.id.phone_sign_up);
        phoneSignUp.setTypeface(customFont);
        email = (TextInputEditText) findViewById(R.id.email_sign_up);
        email.setTypeface(customFont);
        pwdSignUp = (TextInputEditText) findViewById(R.id.pwd_sign_up);
        pwdSignUp.setTypeface(customFont);
        pwdSignUpReq = (TextView) findViewById(R.id.pwd_sign_up_req);
        pwdSignUpReq.setTypeface(customFont);

        tvSignUpInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSignInScreen = false;
                showSignUpForm();
            }
        });

        tvSignInInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSignInScreen = true;
                showSignInForm();
            }
        });
        showSignInForm();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.rotate_right_to_left);
                if (isSignInScreen)
                    btnSignUp.startAnimation(clockwise);
            }
        });

        setValidations();
    }

    public void launchForgotPwd(View view) {
        startActivity(new Intent(this, ForgotPwdActivity.class));
    }

    private void showForm(float infoSignInWidthPercent, float infoSignUpWidthPercent, int visibility,
                          int translateId, LinearLayout layout, int rotateId, Button button) {
        PercentRelativeLayout.LayoutParams paramsSignIn = (PercentRelativeLayout.LayoutParams)
                layoutSignIn.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignIn = paramsSignIn.getPercentLayoutInfo();
        infoSignIn.widthPercent = infoSignInWidthPercent;
        layoutSignIn.requestLayout();

        PercentRelativeLayout.LayoutParams paramsSignUp = (PercentRelativeLayout.LayoutParams)
                layoutSignUp.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignUp = paramsSignUp.getPercentLayoutInfo();
        infoSignUp.widthPercent = infoSignUpWidthPercent;
        layoutSignUp.requestLayout();

        if (visibility == View.GONE) {
            tvSignUpInvoker.setVisibility(visibility);
            tvSignInInvoker.setVisibility(View.VISIBLE);
        } else {
            tvSignUpInvoker.setVisibility(visibility);
            tvSignInInvoker.setVisibility(View.GONE);
        }

        Animation translate = AnimationUtils.loadAnimation(this, translateId);
        layout.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(this, rotateId);
        button.startAnimation(clockwise);
    }

    private void showSignInForm() {
        showForm(0.85f, 0.15f, View.VISIBLE, R.anim.translate_left_to_right, layoutSignIn,
                R.anim.rotate_left_to_right, btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneSignInBool && pwdSignInBool) {
                    new ProcessHandler(AnimationSignInSignUpActivity.this, "Signing in...", "signIn",
                            phoneSignIn.getText().toString(), pwdSignIn.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter valid credentials",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSignUpForm() {
        showForm(0.15f, 0.85f, View.GONE, R.anim.translate_right_to_left, layoutSignUp,
                R.anim.rotate_right_to_left, btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fnameBool && lnameBool && phoneSignUpBool && phoneSignUpBool1 && pwdSignUpBool
                        && pwdSignUpBool1) {
                    new ProcessHandler(AnimationSignInSignUpActivity.this, "Validating phone...",
                            "phoneValidation", phoneSignUp.getText().toString());
                } else if(!phoneSignUpBool1) {
                    phoneSignUp.setError("Invalid Phone Number");
                } else if(!pwdSignUpBool1) {
                    pwdSignUp.setError("At least 6 characters");
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter valid details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setValidations() {
        TextChangedListener textChangedListener = new TextChangedListener(this);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return phoneSignInValidation();
            }
        }, phoneSignIn);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return pwdSignInValidation();}
        }, pwdSignIn);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return fNameValidation();
            }
        }, fname);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return lNameValidation();
            }
        }, lname);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return phoneSignUpValidation();
            }
        }, phoneSignUp);

        textChangedListener.addListener(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {return pwdSignUpValidation();
            }
        }, pwdSignUp);
    }

    @Nullable
    private Boolean phoneSignInValidation() {
        String phone = phoneSignIn.getText().toString();
        phoneSignInBool = !phone.equals("");
        return null;
    }

    @Nullable
    private Boolean pwdSignInValidation() {
        String pwd = pwdSignIn.getText().toString();
        pwdSignInBool = !pwd.equals("");
        return null;
    }

    @Nullable
    private Boolean fNameValidation() {
        fnameBool = !fname.getText().toString().equals("");
        return null;
    }

    @Nullable
    private Boolean lNameValidation() {
        lnameBool = !lname.getText().toString().equals("");
        return null;
    }

    @Nullable
    private Boolean phoneSignUpValidation() {
        String phone = phoneSignUp.getText().toString();
        phoneSignUpBool = !phone.equals("");
        phoneSignUpBool1 = phone.length() == 10;
        return null;
    }

    @Nullable
    private Boolean pwdSignUpValidation() {
        pwdSignUpBool = pwdSignUp.getText().toString().equals("");
        if (pwdSignUp.getText().toString().length() >= 6) {
            pwdSignUpReq.setVisibility(View.INVISIBLE);
            pwdSignUpBool1 = true;
        } else {
            pwdSignUpReq.setVisibility(View.VISIBLE);
            pwdSignUpBool1 = false;
        }
        return null;
    }
}