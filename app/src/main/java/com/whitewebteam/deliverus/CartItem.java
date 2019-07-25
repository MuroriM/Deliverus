package com.whitewebteam.deliverus;

class CartItem extends Item {

    private int pcs, subTotal;

    void setPcs(int pcs) {
        this.pcs = pcs;
    }

    int getPcs() {
        return pcs;
    }

    void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    int getSubTotal() {
        return subTotal;
    }
}
