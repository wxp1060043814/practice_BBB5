package com.example.my.practice_bbb5;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivilegedExceptionAction;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Button button;

    private MyRetrofitApi myRetrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://www.baidu.com/").build();
        myRetrofitApi=retrofit.create(MyRetrofitApi.class);
        init();
    }

    private void init() {
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call<ResponseBody>call=myRetrofitApi.downloadFile("http://img3.imgtn.bdimg.com/it/u=3843979064,1483327667&fm=21&gp=0.jpg");
                        try {
                            retrofit2.Response<ResponseBody>response=call.execute();

                            InputStream inputStream=response.body().byteStream();

                            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/tt.jpg");
                            file.createNewFile();
                            FileOutputStream fileOutputStream=new FileOutputStream(file);
                            byte[]bytes=new byte[1024];
                            int len=inputStream.read(bytes);
                            while (len!=-1){
                                fileOutputStream.write(bytes,0,len);
                                 len=inputStream.read(bytes);


                            }
                            fileOutputStream.flush();
                            inputStream.close();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();







            }
        });
    }
}
