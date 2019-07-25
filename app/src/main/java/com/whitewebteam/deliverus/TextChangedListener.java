package com.whitewebteam.deliverus;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Callable;

class TextChangedListener
{

    private Context context;

    TextChangedListener(Context context) {
        this.context = context;
    }

    void addListener(final Callable<Boolean> validation, EditText... editTexts)
    {
        for (EditText editText : editTexts)
        {
            editText.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    try
                    {
                        validation.call();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context, "Something's not right", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
