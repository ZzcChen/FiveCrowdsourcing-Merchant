package com.example.administrator.fivecrowdsourcing_merchant.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public interface IExpandable<T> {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();
    int getLevel();
}
