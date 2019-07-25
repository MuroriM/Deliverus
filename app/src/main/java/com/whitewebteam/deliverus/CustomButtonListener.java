package com.whitewebteam.deliverus;

import android.widget.TextView;

interface CustomButtonListener {
    void onButtonClickListener(int position, TextView pcs, int value, CartItem cartItem,
                               TextView subTotal);
}
