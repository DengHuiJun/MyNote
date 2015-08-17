package com.zero.mynote.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.Toast;

import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatTextView;
import com.zero.mynote.R;
import com.zero.mynote.db.NoteInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by luowei on 15/4/10.
 */
public class AddActivity extends Activity {
    private FlatTextView timeFTV;
    private FlatButton backBtn;
    private FlatButton okBtn;
    private FlatEditText text;
    private String currentTime;
    private Context context;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        timeFTV = (FlatTextView) findViewById(R.id.tv_add_time);
        backBtn = (FlatButton) findViewById(R.id.btn_back);
        okBtn = (FlatButton) findViewById(R.id.btn_ok);
        text = (FlatEditText) findViewById(R.id.et_text);
        context = this;

        currentTime = getNowTime();
        timeFTV.setText("现在时间："+currentTime);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strText = text.getText().toString();
                if(!strText.equals("")) {
                    NoteInfo noteInfo = new NoteInfo(context);
                    noteInfo.insertNote(currentTime,strText);
                    Toast toast= Toast.makeText(context,"纪录成功！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast toast= Toast.makeText(context,"内容不能为空！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

            }
        });

    }

    private String getNowTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
