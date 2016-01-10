package com.example.ize.jongq;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * Created by Dream on 24/6/2558.
 */
public class GridViewAdapterPromotion extends BaseAdapter {

    private Context context;    //grigview
    private ArrayList<Drawable> images; //grigview
    private GridViewHolder girdGridViewHolder;  //grigview

    //construtor
    //grigview
    public GridViewAdapterPromotion(Context context, ArrayList<Drawable> images) {
        this.context = context;
        this.images = images;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_gridview_item_promotion, null);
            girdGridViewHolder = new GridViewHolder();
            girdGridViewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(girdGridViewHolder);
        } else {
            girdGridViewHolder = (GridViewHolder) convertView.getTag();
        }
        //grigview
        //assing
        girdGridViewHolder.image.setImageDrawable(images.get(position));
        return convertView;
    }

    //grigview
    private class GridViewHolder {
        public ImageView image;
    }
}
