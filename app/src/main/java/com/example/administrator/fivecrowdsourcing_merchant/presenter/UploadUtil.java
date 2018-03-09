package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Administrator on 2018/3/8.
 */

public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码

    /**
     * android上传文件到服务器
     *
     * @param file       图片
     * @param RequestURL 请求的rul  @return  返回响应的内容
     */
    public static void uploadFile(final File file, final String RequestURL, final Merchant merchant) {
        final String BOUNDARY = UUID.randomUUID().toString();  //边界标识   随机生成
        final String PREFIX = "--", LINE_END = "\r\n";
        final String CONTENT_TYPE = "multipart/form-data";   //内容类型
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String merchantRequestURL = RequestURL + "?merchantId=" + merchant.getMerchantid();
                    URL url = new URL(merchantRequestURL );
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(TIME_OUT);
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setDoInput(true);  //允许输入流
                    conn.setDoOutput(true); //允许输出流
                    conn.setUseCaches(false);  //不允许使用缓存
                    conn.setRequestMethod("GET");  //请求方式
                    conn.setRequestProperty("Charset", CHARSET);  //设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                    conn.connect();
                    if (file != null) {
                        /**
                         * 当文件不为空，把文件包装并且上传
                         */
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                        StringBuffer sb = new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
//                        if (merchant != null) {
//                            /**
//                             * 当商户不为空,上传商户id
//                             */
//                            sb.append(PREFIX).append(BOUNDARY).append(LINE_END);//分界符
//                            sb.append("Content-Disposition: form-data; name=\"" + "merchantId" + "\"" + LINE_END);
//                            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
//                            sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
//                            sb.append(LINE_END);
//                            sb.append("2");
//                            sb.append(LINE_END);//换行！
//                        }
//                        sb.append(PREFIX);//开始拼接文件参数
//                        sb.append(BOUNDARY);
//                        sb.append(LINE_END);
                        /**
                         * 这里重点注意：
                         * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                         * filename是文件的名字，包含后缀名的   比如:abc.png
                         */
                        sb.append("Content-Disposition: form-data; name=\"img\"; filename=\"" + file.getName() + "\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());
                        InputStream is = new FileInputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        is.close();
                        dos.write(LINE_END.getBytes());
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                        dos.write(end_data);
                        dos.flush();
                        /**
                         * 获取响应码  200=成功
                         * 当响应成功，获取响应的流
                         */
                        int res = conn.getResponseCode();
                        if (res == 200) {
                            InputStream input = conn.getInputStream();
                            StringBuffer sb1 = new StringBuffer();
                            int ss;
                            while ((ss = input.read()) != -1) {
                                sb1.append((char) ss);
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }
}

