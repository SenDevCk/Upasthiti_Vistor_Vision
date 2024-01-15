package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.EmployeeListData;
import Upasthiti.vistor_vision.in.eAttendance.entity.WorkListData;
import Upasthiti.vistor_vision.in.eAttndance.R;


/**
 * Created by nicsi on 11/23/2017.
 */
public class WorkListAdaptor extends BaseAdapter {
    Context activity;

    ArrayList<WorkListData> Wrklist;

    public WorkListAdaptor(Context context, ArrayList<WorkListData> rlist) {
        this.activity = context;
        this.Wrklist = rlist;

    }

    @Override
    public int getCount() {
        return Wrklist.size();
    }

    @Override
    public Object getItem(int position) {
        return Wrklist.get(position);
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
        row = inflater.inflate(R.layout.child_emp_list, parent, false);
        holder = new ViewHolder();
        holder. ProjectName= (TextView) row.findViewById(R.id.tv_emp_no);
        holder. Emp_No= (TextView) row.findViewById(R.id.tv_emp_name);
        holder.WorkType = (TextView) row.findViewById(R.id.tv_post_name);
        holder.LogType = (TextView) row.findViewById(R.id.tv_blk);
       holder.Date = (TextView) row.findViewById(R.id.tv_date);
        holder.Date.setVisibility(View.VISIBLE);

        holder.ProjectName.setText("Project Name : " + Wrklist.get(position).getProjectName());
        holder.WorkType.setText("Work Type  : "+Wrklist.get(position).getWorkType());
        holder.Emp_No.setText("Employee No : " + Wrklist.get(position).getEmpNo());
       holder.LogType.setText("Log Type : "+Wrklist.get(position).getLogType());
       holder.Date.setText("Log Date : "+Wrklist.get(position).getLogDate());
        row.setTag(holder);


        return row;
    }

    private class ViewHolder {
        TextView Emp_No,ProjectName, WorkType, LogType,Date;
    }

    public void upDateEntries(ArrayList<WorkListData> entries) {
        Wrklist = entries;
        notifyDataSetChanged();
    }


}


