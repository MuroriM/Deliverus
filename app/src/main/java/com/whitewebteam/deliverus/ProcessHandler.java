package com.whitewebteam.deliverus;

import android.app.ProgressDialog;
import android.content.Context;

class ProcessHandler {

    static ProgressDialog progressDialog;

    ProcessHandler(Context context, String message, String... params) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new BackgroundWorker(context).execute(params);
    }
}
