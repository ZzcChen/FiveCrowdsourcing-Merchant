package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.Step2Presenter;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.UploadUtil;

import org.w3c.dom.Document;

import java.io.File;

public class Step2Activity extends AppCompatActivity implements Step2View{
    private TextView title;
    private ImageView business;
    private ImageView foodbusiness;
    private Button clickbusiness;
    private Button clickfoodbusniess;
    private Button secondStep;
    private String buslicensephoto;//工商经营许可证
    private String foodbuslicensephoto;//食品经营许可证存储地址
    private Merchant merchant = new Merchant();
    Step2Presenter step2Presenter = new Step2Presenter(Step2Activity.this);

    private static final int CHOOSE_BUSINESS = 1;
    private static final int CHOOSE_FOOD=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        initView();
    }

    private void initView() {
        //获得商家信息
        merchant= (Merchant) getIntent().getSerializableExtra("merchant");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);
        title.setText("第二步");
        business = findViewById(R.id.business);
        foodbusiness = findViewById(R.id.foodbusiness);
        clickbusiness = findViewById(R.id.click_business);
        clickbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //权限判断
                if (ContextCompat.checkSelfPermission(Step2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Step2Activity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开相册
                    openAlbum(CHOOSE_BUSINESS);
                }
            }
        });
        clickfoodbusniess = findViewById(R.id.click_foodbusiness);
        clickfoodbusniess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //权限判断
                if (ContextCompat.checkSelfPermission(Step2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Step2Activity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开相册
                    openAlbum(CHOOSE_FOOD);
                }
            }
        });
        secondStep = findViewById(R.id.second_step);
        secondStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String requestURL = GlobalParameter.URL + "UploadImage";
                //上传图片
                File buslicense= new File(buslicensephoto);
                File foodbuslicense = new File(foodbuslicensephoto);
                UploadUtil.uploadFile(buslicense, requestURL,merchant);
                UploadUtil.uploadFile(foodbuslicense,requestURL,merchant);
                //图片信息传送
                step2Presenter.sendImage(buslicense.getName(), foodbuslicense.getName(), merchant);
            }
        });
    }

    private void openAlbum(int FLAG) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, FLAG);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意权限才能使用该程序", Toast.LENGTH_SHORT).
                                    show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_BUSINESS:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data,CHOOSE_BUSINESS);
                    }
                }
                break;
            case CHOOSE_FOOD:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data,CHOOSE_FOOD);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data,int FLAG) {
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
                case CHOOSE_BUSINESS:
                    buslicensephoto = imagePath;
                    business.setImageBitmap(bitmap);
                    break;
                case CHOOSE_FOOD:
                    foodbuslicensephoto = imagePath;
                    foodbusiness.setImageBitmap(bitmap);
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
        vw = business.getWidth();
        vh = business.getHeight();//根据身份证尺寸计算

        int scaleFactor = Math.min(iw / vw, ih / vh);//计算缩小比率

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
    public void finishStep2(Merchant merchant) {
        Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
        intent.putExtra("merchant",merchant);
        startActivity(intent);
    }
}

