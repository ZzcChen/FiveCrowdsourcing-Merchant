package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.animation.TimeInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2018/3/14.
 */

public class JellyInterpolator extends LinearInterpolator {
    private float factor;

    public JellyInterpolator() {
        this.factor = 0.15f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input)
                * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}
