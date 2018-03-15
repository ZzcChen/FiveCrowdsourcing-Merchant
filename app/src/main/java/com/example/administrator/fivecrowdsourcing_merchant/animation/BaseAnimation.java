package com.example.administrator.fivecrowdsourcing_merchant.animation;

import android.animation.Animator;
import android.view.View;

/**
 * Created by Administrator on 2018/3/10.
 */

public interface BaseAnimation {
    Animator[] getAnimators(View view);//动画播放器
}
