package com.zero.mynote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.zero.mynote.adapter.MainAdapter;
import com.zero.mynote.db.NoteBean;
import com.zero.mynote.db.NoteInfo;
import com.zero.mynote.ui.AddActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView mainlistview;
    private MainAdapter mainAdapter;
    private List<NoteBean> noteBeanList;

    private long firstTime =0;

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(R.array.blood);
        setContentView(R.layout.main);
        setOverflowShowingAlways();
        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this,R.array.blood,false,2));
        initView();
        new getNotes().execute();
    }

    private void initView() {
        mainlistview = (ListView) findViewById(R.id.main_list_view);

    }

    private class getNotes extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            //处理数据库耗时操作
            NoteInfo noteInfo = new NoteInfo(MainActivity.this);
            noteBeanList = new ArrayList<>();
            noteBeanList = noteInfo.getAllNotes();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mainAdapter = new MainAdapter(MainActivity.this,noteBeanList);
            mainlistview.setAdapter(mainAdapter);
            mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final  NoteBean note = (NoteBean) mainlistview.getItemAtPosition(position);
                    String strTime = note.getNoteTime();
                    String strText = note.getNoteText();
                    noteId = note.getNoteID();
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("时间："+strTime)
                            .setMessage("内容："+strText)
                            .setPositiveButton("取消",null)
                            .setNegativeButton("删除",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NoteInfo noteInfo1 = new NoteInfo(MainActivity.this);
                                    noteInfo1.deleteNote(noteId);
                                    noteBeanList.remove(position);
                                    mainAdapter.notifyDataSetChanged();
                                }
                            })
                            .show();
                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_note) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            finish();

            return true;
        }else if(id == R.id.action_del_all){
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("一键删除")
                    .setMessage("是否删除全部记录？")
                    .setPositiveButton("取消",null)
                    .setNegativeButton("删除",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NoteInfo noteInfo1 = new NoteInfo(MainActivity.this);
                            noteInfo1.deleteAll();
                            noteBeanList.removeAll(noteBeanList);
                            mainAdapter.notifyDataSetChanged();
                        }
                    })
                    .show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    //强制显示右上角设置项
    private void setOverflowShowingAlways(){
        ViewConfiguration configuration = ViewConfiguration.get(this);
        try {
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(configuration,false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //设置按两次退出程序
    public boolean onKeyUp(int KeyCode,KeyEvent event){
        switch (KeyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if(secondTime - firstTime > 2000 ){
                    Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                }else{
                    finish();
                }
                break;
        }
        return super.onKeyUp(KeyCode,event);

    }


}
