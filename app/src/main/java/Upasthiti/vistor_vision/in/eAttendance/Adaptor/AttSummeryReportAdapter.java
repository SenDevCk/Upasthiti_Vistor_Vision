package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.AttendanceSummeryReportData;
import Upasthiti.vistor_vision.in.eAttndance.R;

public class AttSummeryReportAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<AttendanceSummeryReportData> reportEntities;
    LayoutInflater layoutInflater;
    String save_date;

    public AttSummeryReportAdapter(Activity activity, ArrayList<AttendanceSummeryReportData> reportEntities) {
        this.activity = activity;
        this.reportEntities = reportEntities;
        layoutInflater = activity.getLayoutInflater();
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
        String sts = "";
        convertView = layoutInflater.inflate(R.layout.report_attendance_summery_item, null, false);
        TextView text_date = (TextView) convertView.findViewById(R.id.text_date);
        TextView text_status = (TextView) convertView.findViewById(R.id.text_status);
        TextView text_InTime = (TextView) convertView.findViewById(R.id.text_in_time);
        TextView text_OutTime = (TextView) convertView.findViewById(R.id.text_out_time);
        AttendanceSummeryReportData gravanceReportData = reportEntities.get(position);
        text_date.setText(gravanceReportData.get_Date());
        sts = gravanceReportData.get_Status();
        if (sts.equals("P")) {
            text_status.setBackgroundColor(convertView.getResources().getColor(R.color.green));
        }
      else if (sts.equals("A")) {
            text_status.setBackgroundColor(convertView.getResources().getColor(R.color.red));
        }
      else if (sts.equals("L")) {
            text_status.setBackgroundColor(convertView.getResources().getColor(R.color.yellow));
        }else if (sts.equals("LE")) {
            text_status.setBackgroundColor(convertView.getResources().getColor(R.color.orange));
        }if (sts.equals("S")) {
            text_status.setBackgroundColor(convertView.getResources().getColor(R.color.gray));
        }
        if (sts.equals("H")) {
            text_status.setBackgroundColor(convertView.getResources().getColor(R.color.lightblue));
        }
        text_status.setText(gravanceReportData.get_Status());
        text_InTime.setText(gravanceReportData.get_InTime());
        text_OutTime.setText(gravanceReportData.get_OutTime());
        return convertView;
    }
}
