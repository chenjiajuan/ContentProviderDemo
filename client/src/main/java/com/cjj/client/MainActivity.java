package com.cjj.client;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;


public class MainActivity extends Activity {
    private static final String AUTHORITY = "com.cjj.server.contentprovider";
    private static final Uri STUDENT_ALL_URI = Uri.parse("content://" + AUTHORITY + "/student");
    private Button bt_insert;
    private EditText et;
    private TextView tv_content;
    private Button btn_type;
    private boolean flag=false;
    private ContentResolver contentObserver;
    private Cursor cursor;
    private  int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDataFromEdit();
        //addDataFromText();
    }

    private static class  MyHandler extends Handler{
        private WeakReference<MainActivity> activityWeakReference;
        public MyHandler(MainActivity activity){
            activityWeakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity=activityWeakReference.get();
            if (activity==null)
                return;
            activity.cursor=activity.contentObserver.query(STUDENT_ALL_URI,null,
                    null,null,null);
            if (activity.cursor==null)
                return;
            activity.cursor.moveToFirst();
            String text="";
            while (activity.cursor.moveToNext()){
                text+=text;
                activity.tv_content.setText(text);
            }

        }
    }

    private void addDataFromEdit() {
        et =  findViewById(R.id.et);
        bt_insert =  findViewById(R.id.btn_insert);
        btn_type=findViewById(R.id.btn_type);
        tv_content=findViewById(R.id.tv_content);
        contentObserver=getContentResolver();
        contentObserver.registerContentObserver(STUDENT_ALL_URI,
                true,new PersonObserver(new MyHandler(this)));
        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    btn_type.setText("person");
                }else {
                    btn_type.setText("student");
                }
                flag=!flag;

            }
        });
        bt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             ContentValues values=new ContentValues();
             index++;
             String name="Tom"+index;
             String description="girl"+index;
             values.put("name",name);
             values.put("description",description);
             contentObserver.insert(STUDENT_ALL_URI,values);

            }
        });
    }

    private void addDataFromText() {
        FileOutputStream outputStream=null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream=openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("Data to save test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter!=null){
                try {
                    outputStream.close();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
