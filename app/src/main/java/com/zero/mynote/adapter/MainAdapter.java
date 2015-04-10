package com.zero.mynote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cengalabs.flatui.views.FlatTextView;
import com.zero.mynote.R;
import com.zero.mynote.db.NoteBean;

import java.util.List;

/**
 * Created by luowei on 15/4/10.
 */
public class MainAdapter extends BaseAdapter {
    private List<NoteBean> noteBeanList;
    private LayoutInflater layoutInflater;
    private Context context;

    public  MainAdapter(Context context,List<NoteBean> noteBeanList){
        this.context = context;
        this.noteBeanList = noteBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    public List<NoteBean> getNoteBeanList(){
        return noteBeanList;
    }


    @Override
    public int getCount() {
        return noteBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.main_listview_item,null);
            holder.noteTime = (FlatTextView) convertView.findViewById(R.id.tv_notetime);
            holder.noteText = (FlatTextView) convertView.findViewById(R.id.tv_notetext);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        final NoteBean item = (NoteBean) getItem(position);
        holder.noteTime.setText(item.getNoteTime());
        holder.noteText.setText(item.getNoteText());

        return convertView;
    }

    class ViewHolder{
        public FlatTextView noteTime;
        public FlatTextView noteText;
    }
}
