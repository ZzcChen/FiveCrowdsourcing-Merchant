package com.example.administrator.fivecrowdsourcing_merchant.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;

/**
 * @author 冯帅
 * @describe 自定义居中弹出dialog
 */
public class CenterDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String dialogtext;
    private int layoutResID;
    TextView textView;
    TextView dialogTrue;
    TextView dialogCancel;

    /**
     * 要监听的控件id
     */
    private int[] listenedItems;

    private OnCenterItemClickListener listener;

    public CenterDialog(Context context, int layoutResID, int[] listenedItems, String dialogtext) {
        super(context, R.style.dialog_custom);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
        this.dialogtext = dialogtext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(layoutResID);
        //改变dailog文字
        dialogCancel = findViewById(R.id.dialog_cancel);
        dialogTrue = findViewById(R.id.dialog_sure);
        textView = findViewById(R.id.dialog_text);
        textView.setText(dialogtext);
        // 宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);

        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    public void ChangeDialogText(String dialog_true, String dialog_cancel, String content) {
        dialogCancel.setText(dialog_cancel);
        dialogTrue.setText(dialog_true);
        textView.setText(content);
    }

    public interface OnCenterItemClickListener {

        void OnCenterItemClick(CenterDialog dialog, View view);

    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        listener.OnCenterItemClick(this, view);
    }
}

