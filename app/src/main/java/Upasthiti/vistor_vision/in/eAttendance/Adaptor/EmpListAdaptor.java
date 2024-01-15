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
import Upasthiti.vistor_vision.in.eAttndance.R;


/**
 * Created by nicsi on 11/23/2017.
 */
public class EmpListAdaptor extends BaseAdapter {
    Context activity;

    ArrayList<EmployeeListData> Emplist;

    public EmpListAdaptor(Context context, ArrayList<EmployeeListData> rlist) {
        this.activity = context;
        this.Emplist = rlist;

    }

    @Override
    public int getCount() {
        return Emplist.size();
    }

    @Override
    public Object getItem(int position) {
        return Emplist.get(position);
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
        holder.Emp_Name = (TextView) row.findViewById(R.id.tv_emp_name);
        holder.Post_Name = (TextView) row.findViewById(R.id.tv_post_name);
        holder.Block = (TextView) row.findViewById(R.id.tv_blk);
        holder.Emp_No = (TextView) row.findViewById(R.id.tv_emp_no);
       holder.Date = (TextView) row.findViewById(R.id.tv_date);

        holder.Emp_Name.setText("Name : " + Emplist.get(position).getUserName());
        holder.Post_Name.setText("District Name : "+Emplist.get(position).getDistrictName());
        holder.Emp_No.setText("Employee No : " + Emplist.get(position).getEmpNo());
       holder.Block.setText("Block Name : "+Emplist.get(position).getBlockName());
        row.setTag(holder);


        return row;
    }

    private class ViewHolder {
        TextView Emp_Name, Post_Name, Emp_No, Date,Block;
    }

    public void upDateEntries(ArrayList<EmployeeListData> entries) {
        Emplist = entries;
        notifyDataSetChanged();
    }


}


