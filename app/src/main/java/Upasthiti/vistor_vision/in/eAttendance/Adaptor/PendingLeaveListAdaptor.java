package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.PendingLeaveListData;
import Upasthiti.vistor_vision.in.eAttndance.R;


/**
 * Created by nicsi on 11/23/2017.
 */
public class PendingLeaveListAdaptor extends BaseAdapter {
    Context activity;

    ArrayList<PendingLeaveListData> pendingLeaveListData;

    public PendingLeaveListAdaptor(Context context, ArrayList<PendingLeaveListData> rlist) {
        this.activity = context;
        this.pendingLeaveListData = rlist;

    }

    @Override
    public int getCount() {
        return pendingLeaveListData.size();
    }

    @Override
    public Object getItem(int position) {
        return pendingLeaveListData.get(position);
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
        row = inflater.inflate(R.layout.child_pending_leave_list, parent, false);
        holder = new ViewHolder();
        holder.Emp_Name = (TextView) row.findViewById(R.id.tv_ename);
        holder.District = (TextView) row.findViewById(R.id.tv_dist);
        holder.Buniyad = (TextView) row.findViewById(R.id.tv_buniyad);
       holder.Date = (TextView) row.findViewById(R.id.tv_lapply_date);
       holder.Leave_Type = (TextView) row.findViewById(R.id.tv_leave_typ);

     String  Leav=pendingLeaveListData.get(position).getLstatus();
        holder.Emp_Name.setText("Name : " + pendingLeaveListData.get(position).getEmpName());
        holder.District.setText("Dirstrict : "+pendingLeaveListData.get(position).getDistrict());
        holder.Buniyad.setText("Emp No : " + pendingLeaveListData.get(position).getEmpNo());

        if(Leav.equals("Approved"))
        {
            holder.Leave_Type.setText("Leave Status : " + pendingLeaveListData.get(position).getLstatus());
            holder.Leave_Type.setTextColor(row.getResources().getColor(R.color.blackTextColor));
        }
        else if(Leav.equalsIgnoreCase("Pending for Approval"))
        {
            holder.Leave_Type.setText("Leave Type : " + pendingLeaveListData.get(position).getLeaveType());
            holder.Leave_Type.setTextColor(row.getResources().getColor(R.color.red));
        }
        else {
            holder.Leave_Type.setText("Leave Status : " + pendingLeaveListData.get(position).getLstatus());
            holder.Leave_Type.setTextColor(row.getResources().getColor(R.color.red));
        }

       holder.Date.setText( "Apply Date : " +pendingLeaveListData.get(position).getApplyDate());
        row.setTag(holder);


        return row;
    }

    private class ViewHolder {
        TextView Emp_Name, District, Buniyad, Date,Leave_Type;
    }

    public void upDateEntries(ArrayList<PendingLeaveListData> entries) {
        pendingLeaveListData = entries;
        notifyDataSetChanged();
    }


}


