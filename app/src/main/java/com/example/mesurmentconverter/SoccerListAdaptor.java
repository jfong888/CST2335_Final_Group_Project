package com.example.mesurmentconverter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SoccerListAdaptor extends BaseAdapter {
    private ArrayList<SoccerObject> soccerList;
    private Context mContext;

    public SoccerListAdaptor(Context context, ArrayList<SoccerObject> soccerList){
        super();
        this.mContext = context;
        this.soccerList = soccerList;
    }

    public int getCount(){
        return this.soccerList.size();
    }

    public SoccerObject getItem(int position){
        return this.soccerList.get(position);
    }

    public long getItemId(int pos){
        return pos;
    }

    public View getView(int p, View recycled, ViewGroup parent) {
        View thisRow = recycled;
        SoccerObject soccerObject = getItem(p);
        thisRow = ((Activity) this.mContext).getLayoutInflater().inflate(R.layout.soccer_layout, null);
        return thisRow;
    }
}
