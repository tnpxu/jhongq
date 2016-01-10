package com.example.ize.jongq;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Dream on 24/6/2558.
 */
public class GridViewAdapter extends BaseAdapter{

    private Context context;    //grigview
    private ArrayList<Drawable> images; //grigview
    private GridViewHolder girdGridViewHolder;  //grigview
    private String[] res;
    private String[] resnum;

    //construtor
    //grigview
    public GridViewAdapter(Context context,ArrayList<Drawable> images,String[] res,String[] resnum){
        this.context = context;
        this.images = images;
        this.res = res;
        this.resnum = resnum;
    }
    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //grigview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_gridview_item,null);
            TextView textViewRes = (TextView) convertView.findViewById(R.id.textView4);
            TextView textViewResNum = (TextView) convertView.findViewById(R.id.textView3);
            textViewRes.setText(res[position]);
            textViewResNum.setText(resnum[position]);
            girdGridViewHolder = new GridViewHolder();
            girdGridViewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(girdGridViewHolder);
        }else {
            girdGridViewHolder = (GridViewHolder) convertView.getTag();
        }
        //grigview
        //assing
        girdGridViewHolder.image.setImageDrawable(images.get(position));
        return convertView;
    }
    //grigview
    private class GridViewHolder{
        public ImageView image;
    }
}
