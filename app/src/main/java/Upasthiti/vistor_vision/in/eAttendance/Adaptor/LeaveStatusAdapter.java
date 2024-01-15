package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveStatusData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class LeaveStatusAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<LeaveStatusData> reportEntities;
    LayoutInflater layoutInflater;
    String save_date;
    private int lastPosition = -1;
    public LeaveStatusAdapter(Activity activity, ArrayList<LeaveStatusData> reportEntities){
        this.activity=activity;
        this.reportEntities=reportEntities;
        layoutInflater=activity.getLayoutInflater();
        //Collections.reverse(this.reportEntities);
    }
    @Override
    public int getCount() {
        return (int) reportEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return reportEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.view_leave_status, null, false);
        TextView Leave_Type=(TextView)convertView.findViewById(R.id.text_leave_typ);
        TextView Total_Leave=(TextView)convertView.findViewById(R.id.totl_leave);
        TextView Used_Leave=(TextView)convertView.findViewById(R.id.Used_Leave);
        TextView Avil_Leave=(TextView)convertView.findViewById(R.id.Avil_Leave);

        LeaveStatusData usercotributiondata=reportEntities.get(position);
        Leave_Type.setText(usercotributiondata.getLeaveType());
        Total_Leave.setText(usercotributiondata.getTotalLeave());
        Used_Leave.setText(usercotributiondata.getUsedLeave());
        Avil_Leave.setText(usercotributiondata.getAvilabelLeave());
        setAnimation(Leave_Type,Total_Leave,Used_Leave,Avil_Leave,position);

        return convertView;
    }
    private void setAnimation(View viewToAnimate, TextView total_Leave, TextView used_Leave, TextView avil_Leave, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            total_Leave.startAnimation(animation);
            used_Leave.startAnimation(animation);
            avil_Leave.startAnimation(animation);
            lastPosition = position;
        }
    }
}
