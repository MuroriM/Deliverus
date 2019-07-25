package com.whitewebteam.deliverus;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;

public class FadingImageView extends android.support.v7.widget.AppCompatImageView {

    private FadeSide fadeSide;

    private Context context;

    private enum FadeSide {
        RIGHT_SIDE, LEFT_SIDE, TOP_SIDE, BOTTOM_SIDE
    }

    public FadingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public FadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FadingImageView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        // Enable horizontal fading
        this.setHorizontalFadingEdgeEnabled(true);
        // Apply default fading length
        this.setEdgeLength(14);
        // Apply default side
        this.setFadeDirection(FadeSide.LEFT_SIDE);
    }

    public void setFadeDirection(FadeSide side) {
        this.fadeSide = side;
    }

    public void setEdgeLength(int length) {
        this.setFadingEdgeLength(getPixels(length));
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return fadeSide.equals(FadeSide.LEFT_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return fadeSide.equals(FadeSide.RIGHT_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return fadeSide.equals(FadeSide.TOP_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return fadeSide.equals(FadeSide.BOTTOM_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return true;
    }

    @Override
    public boolean onSetAlpha(int alpha) {
        return false;
    }

    private int getPixels(int dipValue) {
        Resources r = context.getResources();

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue, r.getDisplayMetrics());
    }
}