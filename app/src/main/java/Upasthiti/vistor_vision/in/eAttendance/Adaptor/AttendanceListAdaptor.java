package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.Utilitites.Utiilties;
import Upasthiti.vistor_vision.in.eAttendance.entity.MarkedAttendanceData;
import Upasthiti.vistor_vision.in.eAttndance.R;


/**
 * Created by nicsi on 11/23/2017.
 */
public class AttendanceListAdaptor extends BaseAdapter {
    Context activity;

    ArrayList<MarkedAttendanceData> AttendanceRptList;

    public AttendanceListAdaptor(Context context, ArrayList<MarkedAttendanceData> rlist) {
        this.activity = context;
        this.AttendanceRptList = rlist;

    }

    @Override
    public int getCount() {
        return AttendanceRptList.size();
    }

    @Override
    public Object getItem(int position) {
        return AttendanceRptList.get(position);
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
        row = inflater.inflate(R.layout.child_marked_att_list, parent, false);
        holder = new ViewHolder();
        holder.Emp_Name = (TextView) row.findViewById(R.id.tv_emp_name);
        holder.In_Time = (TextView) row.findViewById(R.id.tv_in_time);
        holder.Out_Time = (TextView) row.findViewById(R.id.tv_chk_out_time);
        holder.Date = (TextView) row.findViewById(R.id.tv_date);
        holder.Image = (ImageView) row.findViewById(R.id.img_AttImg);

        holder.Emp_Name.setText("Name : " + AttendanceRptList.get(position).getUserName());
        holder.In_Time.setText("Check In Time : "+AttendanceRptList.get(position).getInTime());
        holder.Out_Time.setText("Check Out Time : " + AttendanceRptList.get(position).getOutTime());
        holder.Date.setText(Utiilties.getCurrentDate());
        Picasso.with(activity).load("http://www.bedmc.in/" + AttendanceRptList.get(position).getInPhoto()).error(R.drawable.noimage).into(holder.Image);

        row.setTag(holder);


        return row;
    }

    private class ViewHolder {
        TextView Emp_Name, In_Time, Out_Time, Date;
        ImageView Image;
    }

    public void upDateEntries(ArrayList<MarkedAttendanceData> entries) {
        AttendanceRptList = entries;
        notifyDataSetChanged();
    }


}


