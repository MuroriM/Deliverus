package com.whitewebteam.deliverus;

import android.app.Application;

public final class MyDeliverus extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/quicksand_bold.ttf");
        FontsOverride.setDefaultFont(this, "SANS", "fonts/quicksand_bold.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/quicksand_bold.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/quicksand_bold.ttf");

        TypeFaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/quicksand_bold.ttf");
    }
}