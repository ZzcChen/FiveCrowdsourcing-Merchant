package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.Step3Presenter;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.UploadUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Step3Activity extends AppCompatActivity implements Step3View{
    private Merchant merchant = new Merchant();
    TextView title;
    private TextView nextstep;
    ImageView idcard;
    Button click_idcard;
    EditText name_edit;
    EditText idcardnumber_edit;
    Button thrid_step;
    String idcardphoto;
    List<StepBean> stepsBeanList = new ArrayList<>();
    Step3Presenter step3Presenter = new Step3Presenter(Step3Activity.this);

    private static final int CHOOSE_IDCARD=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);
        initData();
        initView();
    }

    private void initData() {
        StepBean stepBean0 = new StepBean("基本信息",1);
        StepBean stepBean1 = new StepBean("身份信息",1);
        StepBean stepBean2 = new StepBean("资质证书",0);
        StepBean stepBean3 = new StepBean("已完成",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
    }

    private void initView() {
        //获得商家信息
        merchant= (Merchant) getIntent().getSerializableExtra("merchant");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);
        title.setText("第三步");
        nextstep=findViewById(R.id.next_step);
        nextstep.setText("提交 ");
        HorizontalStepView setpview = (HorizontalStepView) findViewById(R.id.step_view1);
        setpview
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.colorPrimary))//设置 StepsViewIndicator 完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.colorAccent))//设置 StepsViewIndicator 未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.darkorange))//设置 StepsView text 完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))//设置 StepsView text 未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置 StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置 StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置 StepsViewIndicator

        idcard = findViewById(R.id.idcard);
        click_idcard = findViewById(R.id.click_idcard);
        //上传身份证
        click_idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum(CHOOSE_IDCARD);
            }
        });
        name_edit = findViewById(R.id.name_edit);
        idcardnumber_edit = findViewById(R.id.idcardnumber_edit);
        thrid_step = findViewById(R.id.third_step);
        nextstep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String requestURL = GlobalParameter.URL + "UploadImage";
                //上传身份证
                File idcardfile= new File(idcardphoto);
                UploadUtil.uploadFile(idcardfile, requestURL,merchant);
                //身份证信息传送
                step3Presenter.sendImage(String.valueOf(name_edit.getText()),String.valueOf(idcardnumber_edit.getText()), merchant,idcardfile.getName());
            }
        });
    }

    private void openAlbum(int FLAG) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,FLAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_IDCARD:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data,CHOOSE_IDCARD);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data, int FLAG) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过doucument id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImge(imagePath,FLAG);//根据图片路径显示图片
    }

    private void displayImge(String imagePath, int FLAG) {
        Bitmap bitmap;
        if (imagePath != null) {
            bitmap=zoomIn(imagePath);//缩小照片
            switch (FLAG) {
                case CHOOSE_IDCARD:
                   idcardphoto = imagePath;
                    idcard.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(this, "加载图片失败!", Toast.LENGTH_SHORT).show();
        }

    }

    private Bitmap zoomIn(String imagePath) {
        int iw, ih, vw, vh;//图片的宽度、高度 imageView的宽度高度
        Bitmap bitmap;
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;//设置选项：只读取图像文件信息而不加载图像文件
        BitmapFactory.decodeFile(imagePath, option);

        iw = option.outWidth;
        ih = option.outHeight;
        vw = idcard.getWidth();
        vh =idcard.getHeight();//根据身份证尺寸计算

        int scaleFactor = Math.max(iw / vw, ih / vh);//计算缩小比率

        option.inJustDecodeBounds = false;//关闭选项
        option.inSampleSize = scaleFactor;//设置缩小比率

        bitmap = BitmapFactory.decodeFile(imagePath, option);
        return bitmap;
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void finishStep3(Merchant merchant) {
        //Toast.makeText(Step3Activity.this, "您已经填写完成所有信息，请等待审核!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Step3Activity.this, MainActivity.class);
        intent.putExtra("merchant",merchant);
        startActivity(intent);
    }
}
