package com.example.administrator.fivecrowdsourcing_merchant.view;

import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;

/**
 * Created by Administrator on 2018/3/6.
 */

public interface MerchantInfoView {
    //跳转地图
    void gotomap();
    //第一步注册完成
   void finishStep1(Merchant merchant);
}
