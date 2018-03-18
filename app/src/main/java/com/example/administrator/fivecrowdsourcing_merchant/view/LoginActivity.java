package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.LoginPresenter;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements LoginView {
    public static String URL = GlobalParameter.URL;
    private TextView login;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    private EditText phone;
    private EditText password;
    private View progress;
    private View mInputLayout;
    private float mWidth, mHeight;
    private LinearLayout mPhone, mPsw;
    private TextView gotoRegister;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_login);
        setContentView(R.layout.login);
        initView();
    }

    /**
     * 输入框的动画效果
     *
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            params.leftMargin = (int) value;
            params.rightMargin = (int) value;
            view.setLayoutParams(params);
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }


    private void initView() {


        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mPhone = findViewById(R.id.input_layout_phone);
        mPsw = findViewById(R.id.input_layout_psw);

        login = findViewById(R.id.login);
        phone = findViewById(R.id.phone);

        password = findViewById(R.id.password);
        login.setOnClickListener(view -> {
            // 计算出控件的高与宽
            mWidth = login.getMeasuredWidth();
            mHeight = login.getMeasuredHeight();
            // 隐藏输入框
            mPhone.setVisibility(View.INVISIBLE);
            mPsw.setVisibility(View.INVISIBLE);
            inputAnimator(mInputLayout, mWidth, mHeight);
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    Thread.sleep(3000);//休眠3秒
                    loginPresenter.Login(String.valueOf(phone.getText()), password.getText().toString(), URL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        });
        gotoRegister = findViewById(R.id.gotoRegister);
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccess(Merchant merchant) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("merchant", merchant);
        startActivity(intent);
    }

    @Override
    public void onFailed() {
        runOnUiThread(() -> {
            Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();

            progress.setVisibility(View.GONE);
            mInputLayout.setVisibility(View.VISIBLE);
            mPhone.setVisibility(View.VISIBLE);
            mPsw.setVisibility(View.VISIBLE);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
            params.leftMargin = 0;
            params.rightMargin = 0;
            mInputLayout.setLayoutParams(params);


            ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
            animator2.setDuration(500);
            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.start();
        });

    }

}
