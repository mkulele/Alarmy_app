package com.alarmy.konyang.alarmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ListItem> listItems;
    TextView category;
    public  ListAdapter(Context context,ArrayList<ListItem> listItems){
        this.context=context;
        this.listItems=listItems;
    }
    public int getCount(){
        return listItems.size();
    }
    public ListItem getItem(int position){
        return listItems.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_list,parent, false);
        }
        category = (TextView) convertView.findViewById(R.id.category);
        category.setText(listItems.get(position).getCategory());
        return convertView;
    }
}
