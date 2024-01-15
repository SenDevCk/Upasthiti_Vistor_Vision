package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.HolidaysData;
import Upasthiti.vistor_vision.in.eAttndance.R;


/**
 * Created by nicsi on 11/23/2017.
 */
public class HolidayListAdaptor extends BaseAdapter {
    Context activity;
    private int lastPosition = -1;
    ArrayList<HolidaysData> HolidayRptList;

    public HolidayListAdaptor(Context context, ArrayList<HolidaysData> rlist) {
        this.activity = context;
        this.HolidayRptList = rlist;

    }

    @Override
    public int getCount() {
        return HolidayRptList.size();
    }

    @Override
    public Object getItem(int position) {
        return HolidayRptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;
        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
        row = inflater.inflate(R.layout.child_holiday_list, parent, false);
        holder = new ViewHolder();
        holder.Day = (TextView) row.findViewById(R.id.tv_Day);
        holder.Date = (TextView) row.findViewById(R.id.tv_date);
        holder.Remarks = (TextView) row.findViewById(R.id.tv_rmrk);
        holder.BG = (TextView) row.findViewById(R.id.txt_bg);



        holder.Day.setText(HolidayRptList.get(position).getDay());
        holder.Date.setText(HolidayRptList.get(position).getDate());
        holder.Remarks.setText(HolidayRptList.get(position).getRemarks());
        setAnimation(holder.Day,holder.Date,holder.Remarks, position);


        if (position%4 == 0){
            holder.BG.setBackgroundColor(Color.parseColor("#1e86cf"));
        } else if (position%4 == 1){
            holder.BG.setBackgroundColor(Color.parseColor("#6E5DB4"));
        } else if (position%4 == 2){
            holder.BG.setBackgroundColor(Color.parseColor("#A504B3"));
        } else if (position%4 == 3){
            holder.BG.setBackgroundColor(Color.parseColor("#2ceae3"));
        }

        row.setTag(holder);
        return row;
    }

    private class ViewHolder {
        TextView Day, Date, Remarks,BG;
    }

    public void upDateEntries(ArrayList<HolidaysData> entries) {
        HolidayRptList = entries;
        notifyDataSetChanged();
    }
    private void setAnimation(View viewToAnimate, TextView date, TextView remarks, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            date.startAnimation(animation);
            remarks.startAnimation(animation);
            lastPosition = position;
        }
    }

}


